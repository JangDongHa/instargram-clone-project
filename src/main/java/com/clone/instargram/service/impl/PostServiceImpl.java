package com.clone.instargram.service.impl;

import com.clone.instargram.domain.comment.Comment;
import com.clone.instargram.domain.comment.CommentRepository;
import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.post.PostRepository;
import com.clone.instargram.domain.post.like.PostLikeRepository;
import com.clone.instargram.domain.tag.Tag;
import com.clone.instargram.domain.tag.TagRepository;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.ResponsePostDto;
import com.clone.instargram.dto.request.PostDto;
import com.clone.instargram.dto.request.UpdatePostDto;
import com.clone.instargram.exception.definition.PostExceptionNaming;
import com.clone.instargram.service.PostService;
import com.clone.instargram.service.definition.PostReturnNaming;
import com.clone.instargram.util.AwsS3Connector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User userPS = userRepository.findByUsername(usernameTK).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_USER));
        if (!postRepository.existsByUserAndId(userPS, updatePostDto.getId()))
            throw new IllegalArgumentException(PostExceptionNaming.NEED_AUTHORIZED);


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
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST));

        List<Tag> tags = tagRepository.findAllByPost(post).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_TAGS));
        List<Comment> comments = commentRepository.findAllByPost(post).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.ERROR_POST_COMMENTS));

        return new ResponsePostDto(post, tags, comments);
    }

    private String updateFileToS3(UpdatePostDto updatePostDto){
        String recentImageSource = postRepository.findById(updatePostDto.getId()).orElseThrow(() -> new IllegalArgumentException(PostExceptionNaming.CANNOT_FIND_POST)).getImageSource();
        awsS3Connector.deleteFileV1(recentImageSource);
        return awsS3Connector.uploadFileV1(updatePostDto.getFile());
    }
}
