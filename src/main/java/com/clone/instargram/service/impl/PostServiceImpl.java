package com.clone.instargram.service.impl;

import com.clone.instargram.domain.comment.Comment;
import com.clone.instargram.domain.comment.CommentRepository;
import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.post.PostRepository;
import com.clone.instargram.domain.post.like.PostLike;
import com.clone.instargram.domain.post.like.PostLikeMapping;
import com.clone.instargram.domain.post.like.PostLikeRepository;
import com.clone.instargram.domain.tag.Tag;
import com.clone.instargram.domain.tag.TagRepository;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.ResponsePostDto;
import com.clone.instargram.dto.ResponsePostLikeUserDto;
import com.clone.instargram.dto.ResponsePostListDto;
import com.clone.instargram.dto.request.PostDto;
import com.clone.instargram.dto.request.UpdatePostDto;
import com.clone.instargram.exception.definition.PostExceptionNaming;
import com.clone.instargram.service.PostService;
import com.clone.instargram.service.definition.PostReturnNaming;
import com.clone.instargram.util.AwsS3Connector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    @Transactional
    public String createPost(PostDto postDto, String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));

        String imageSource = awsS3Connector.uploadFileV1(postDto.getFile());
        postDto.setImageSource(imageSource);
        Post post = postDto.toPost(userPS);
        postRepository.save(post);

        postDto.getTags().forEach(tag-> tagRepository.save(postDto.toTag(post, tag))); // map 사용 시 저장이 안되는 것 같아서 foreach 사용
        return PostReturnNaming.POST_COMPLETE;
    }


    @Override
    @Transactional
    public String updatePost(UpdatePostDto updatePostDto, String usernameTK){
        User userPS = getValidUser(usernameTK, updatePostDto.getId());

        String imageSource = updateFileToS3(updatePostDto);
        postRepository.save(updatePostDto.toPost(userPS, imageSource));
        Post postPS = postRepository.findById(updatePostDto.getId()).orElseThrow();

        tagRepository.deleteAllByPost(postPS);
        updatePostDto.getTags().forEach(tag -> tagRepository.save(updatePostDto.toTag(postPS, tag)));

        return PostReturnNaming.POST_UPDATE_COMPLETE;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponsePostDto getPost(long postId){
        Post postPS = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));

        List<Tag> tags = tagRepository.findAllByPost(postPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_TAGS));
        List<Comment> comments = commentRepository.findAllByPost(postPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_COMMENTS));

        return new ResponsePostDto(postPS, tags, comments);
    }

    @Override
    @Transactional
    public String deletePost(long postId, String usernameTK){
        validCheck(usernameTK, postId);
        Post postPS = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));
        String imageSource = postPS.getImageSource();

        postRepository.delete(postPS);
        awsS3Connector.deleteFileV1(imageSource);

        return PostReturnNaming.POST_DELETE_COMPLETE;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponsePostLikeUserDto> getPostLikeUsers(long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));
        List<PostLikeMapping> users = postLikeRepository.findUsersByPost(post).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_LIKE));

        List<ResponsePostLikeUserDto> dto = new ArrayList<>();
        users.forEach(m -> dto.add(new ResponsePostLikeUserDto(m.getUser())));

        return dto;
    }

    @Override
    @Transactional
    public String postLike(long postId, String usernameTK){
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));
        ResponsePostDto dto = new ResponsePostDto(post);

        if(postLikeRepository.existsByUserAndPost(userPS, post)){
            PostLike postLikePS = postLikeRepository.findByUserAndPost(userPS, post).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.ERROR_POST_LIKE));
            postLikeRepository.delete(postLikePS);

            dto.setLikesCount(dto.getLikesCount() - 1);
            postRepository.save(dto.toPost(post));
            return PostReturnNaming.POST_LIKE_CANCEL_COMPLETE;
        }

        PostLike postLike = PostLike.builder()
                .user(userPS)
                .post(post)
                .build();


        postLikeRepository.save(postLike);
        dto.setLikesCount(dto.getLikesCount() + 1);
        postRepository.save(dto.toPost(post));

        return PostReturnNaming.POST_LIKE_COMPLETE;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponsePostListDto> getPostList(String username){
        User userPS = userRepository.findByUsername(username).orElseThrow(()-> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));

        List<Post> postsPS = postRepository.findAllByUser(userPS).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST));
        List<ResponsePostListDto> dtos = new ArrayList<>();

        postsPS.forEach(post -> dtos.add(new ResponsePostListDto(post, commentRepository.countByPost(post).orElseThrow(()->new IllegalArgumentException(PostExceptionNaming.ERROR_POST_LIKE)))));

        return dtos;
    }



    private String updateFileToS3(UpdatePostDto updatePostDto){
        String recentImageSource = postRepository.findById(updatePostDto.getId()).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST)).getImageSource();
        awsS3Connector.deleteFileV1(recentImageSource);
        return awsS3Connector.uploadFileV1(updatePostDto.getFile());
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
