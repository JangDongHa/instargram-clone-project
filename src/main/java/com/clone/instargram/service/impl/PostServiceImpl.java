package com.clone.instargram.service.impl;

import com.clone.instargram.domain.comment.Comment;
import com.clone.instargram.domain.comment.CommentRepository;
import com.clone.instargram.domain.comment.like.CommentLikeRepository;
import com.clone.instargram.domain.follow.FollowRepository;
import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.post.PostRepository;
import com.clone.instargram.domain.post.like.PostLike;
import com.clone.instargram.domain.post.like.PostLikeMapping;
import com.clone.instargram.domain.post.like.PostLikeRepository;
import com.clone.instargram.domain.tag.Tag;
import com.clone.instargram.domain.tag.TagRepository;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.*;
import com.clone.instargram.dto.request.PostStringDto;
import com.clone.instargram.dto.request.UpdatePostDto;
import com.clone.instargram.dto.request.UpdatePostStringDto;
import com.clone.instargram.exception.definition.PostExceptionNaming;
import com.clone.instargram.service.PostService;
import com.clone.instargram.service.definition.PostReturnNaming;
import com.clone.instargram.util.AwsS3Connector;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    private final AwsS3Connector awsS3Connector;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;
    private final FollowRepository followRepository;

//    @Override
//    @Transactional
//    public String createPost(PostDto postDto, String usernameTK){
//        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));
//
//        String imageSource = awsS3Connector.uploadFileV1(postDto.getFile());
//        postDto.setImageSource(imageSource);
//        Post post = postDto.toPost(userPS);
//        postRepository.save(post);
//
//        postDto.getTags().forEach(tag-> tagRepository.save(postDto.toTag(post, tag))); // map 사용 시 저장이 안되는 것 같아서 foreach 사용
//        return PostReturnNaming.POST_COMPLETE;
//    }

    @Override
    @Transactional
    public String createPost(PostStringDto postDto, String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));

        Post postPS = postRepository.save(postDto.toPost(userPS));
        tagRepository.save(postDto.toTag(postPS));

        return PostReturnNaming.POST_COMPLETE;
    }

    @Override
    @Transactional
    public String updatePost(UpdatePostStringDto updatePostDto, String usernameTK){
        validCheck(usernameTK, updatePostDto.getId());

        Post postPS = postRepository.findById(updatePostDto.getId()).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));
        List<Tag> tag = tagRepository.findAllByPost(postPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_TAGS));

        postRepository.save(updatePostDto.toPost(postPS));
        tagRepository.save(updatePostDto.toTag(tag.get(0)));

        return PostReturnNaming.POST_UPDATE_COMPLETE;
    }


//    @Override
//    @Transactional
//    public String updatePost(UpdatePostDto updatePostDto, String usernameTK){
//        validCheck(usernameTK, updatePostDto.getId());
//
//        String imageSource = updateFileToS3(updatePostDto);
//        Post postPS = postRepository.findById(updatePostDto.getId()).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.ERROR_POST));
//        postRepository.save(updatePostDto.toPost(postPS, imageSource));
//
//        tagRepository.deleteAllByPost(postPS);
//        updatePostDto.getTags().forEach(tag -> tagRepository.save(updatePostDto.toTag(postPS, tag)));
//
//        return PostReturnNaming.POST_UPDATE_COMPLETE;
//    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePostDto getPost(long postId, String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));
        Post postPS = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));

        List<Tag> tags = tagRepository.findAllByPost(postPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_TAGS));
        List<Comment> comments = commentRepository.findAllByPost(postPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_COMMENTS));
        List<ResponseCommentDto> commentDtos = comments.stream().map(comment -> new ResponseCommentDto(comment, commentLikeRepository.existsByUserAndComment(userPS, comment))).toList();

        return new ResponsePostDto(postPS, tags, commentDtos);
    }

    @Override
    @Transactional
    public String deletePost(long postId, String usernameTK){
        validCheck(usernameTK, postId);
        Post postPS = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));
        //String imageSource = postPS.getImageSource();

        List<Tag> tag = tagRepository.findAllByPost(postPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_TAGS));
        postRepository.delete(postPS);

        //awsS3Connector.deleteFileV1(imageSource);

        return PostReturnNaming.POST_DELETE_COMPLETE;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponsePostLikeUserDto> getPostLikeUsers(long postId, String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));
        List<PostLikeMapping> users = postLikeRepository.findUsersByPost(post).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_LIKE));

        List<ResponsePostLikeUserDto> dtoList = new ArrayList<>();
        users.forEach(m -> dtoList.add(new ResponsePostLikeUserDto(m.getUser(), followRepository.existsByToUserAndFromUser(m.getUser(), userPS))));

        return dtoList;
    }

    @Override
    @Transactional
    public Boolean postLike(long postId, String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));
        UpdatePostDto dto = new UpdatePostDto();

        if(postLikeRepository.existsByUserAndPost(userPS, post)){
            PostLike postLikePS = postLikeRepository.findByUserAndPost(userPS, post).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.ERROR_POST_LIKE));
            postLikeRepository.delete(postLikePS);

            postRepository.save(dto.toPost(post, post.getLikesCount() - 1));
            return false;
        }

        PostLike postLike = PostLike.builder()
                .user(userPS)
                .post(post)
                .build();


        postLikeRepository.save(postLike);
        postRepository.save(dto.toPost(post, post.getLikesCount() + 1));

        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponsePostListDto> getPostList(String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(()-> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));

        List<Post> postsPS = postRepository.findAllByUserOrderByIdDesc(userPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST));
        List<ResponsePostListDto> dtoList = new ArrayList<>();

        postsPS.forEach(post -> dtoList.add(new ResponsePostListDto(post, commentRepository.countByPost(post).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.ERROR_POST_LIKE)))));

        return dtoList;
    }

    @Override
    @Transactional
    public List<ResponsePostRecentListDto> getRecentPostList(String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(()->new IllformedLocaleException(PostExceptionNaming.CANNOT_FIND_USER));
        List<Post> postsPS = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<ResponsePostRecentListDto> dtoList = postsPS.stream().map(post ->
                        new ResponsePostRecentListDto(post, commentRepository.countByPost(post).orElse(0L), tagRepository.findAllByPost(post).orElse(null).get(0)
                                , postLikeRepository.existsByUserAndPost(userPS, post))).toList();


        return dtoList;
    }



    private String updateFileToS3(UpdatePostDto updatePostDto){
        String recentImageSource = postRepository.findById(updatePostDto.getId()).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST)).getImageSource();
        String filePath = awsS3Connector.uploadFileV1(updatePostDto.getFile());
        awsS3Connector.deleteFileV1(recentImageSource);
        return filePath;
    }

    private void validCheck (String usernameTK, long postId){
        getValidUser(usernameTK, postId);
    }

    private User getValidUser(String usernameTK, long postId){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));
        if (!postRepository.existsByUserAndId(userPS, postId))
            throw new IllegalArgumentException(PostExceptionNaming.NEED_AUTHORIZED);
        return userPS;
    }
}
