package com.clone.instargram.domain;

import com.clone.instargram.domain.comment.Comment;
import com.clone.instargram.domain.comment.CommentRepository;
import com.clone.instargram.domain.comment.like.CommentLike;
import com.clone.instargram.domain.comment.like.CommentLikeMapping;
import com.clone.instargram.domain.comment.like.CommentLikeRepository;
import com.clone.instargram.domain.follow.Follow;
import com.clone.instargram.domain.follow.FollowRepository;
import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.post.PostRepository;
import com.clone.instargram.domain.post.like.PostLike;
import com.clone.instargram.domain.post.like.PostLikeMapping;
import com.clone.instargram.domain.post.like.PostLikeRepository;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SqlTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;



    @BeforeEach
    public void settingData(){
        User user = User.builder().username("test").password("test").email("tset").profileImage("tesrt").build();
        User user2 = User.builder().username("test2").password("test").email("tset2").profileImage("tesrt").build();
        userRepository.save(user);
        userRepository.save(user2);

        Follow follow = Follow.builder().toUser(user).fromUser(user2).build();
        Post post = Post.builder().user(user).description("test").imageSource("test").build();
        PostLike postLike = PostLike.builder().post(post).user(user).build();
        Comment comment = Comment.builder().post(post).content("testContent").user(user).build();
        CommentLike commentLike = CommentLike.builder().comment(comment).user(user).build();

        postRepository.save(post);
        commentRepository.save(comment);
        commentLikeRepository.save(commentLike);

        postLikeRepository.save(postLike);

        followRepository.save(follow);
    }

    // FollowRepository
    @Test
    @Transactional
    public void isFollowedTest(){ // isFollowed ????????? ??? ??????
        // Given
        User userPS = userRepository.findByUsername("test").orElseThrow();
        User user2PS = userRepository.findByUsername("test2").orElseThrow();

        // When
        boolean isExist = followRepository.existsByToUserAndFromUser(userPS, user2PS).orElseThrow();

        // Then
        assertTrue(isExist);
    }


    // PostRepository
    @Test
    @Transactional
    public void countByUserTest(){ // User ??? ????????? Post ??????
        // Given
        User userPS = userRepository.findByUsername("test").orElseThrow();
        // When
        long count = postRepository.countByUser(userPS).orElseThrow();
        // Then
        assertEquals(1, count);
    }

    // PostLikeRepository
    @Test
    @Transactional
    public void findUsersByPost(){ // Post ??? ????????? ??? ?????? List ?????? (return : List<PostLikeMapping>)
        // Given
        Post postPS = postRepository.findAll().get(0);
        User userPS = userRepository.findByUsername("test").orElseThrow();
        System.out.println("test");
        // When
        List<PostLikeMapping> userList = postLikeRepository.findUsersByPost(postPS).orElseThrow();
        // Then
        assertEquals(1, userList.size());
        assertEquals(userPS.getUsername(), userList.get(0).getUser().getUsername());
    }

    // PostLikeRepository
    @Test
    @Transactional
    public void countByPostTest(){ // Post ??? ????????? ??? ?????? ??????
        // Given
        Post postPS = postRepository.findAll().get(0);
        // When
        long postLikeCount = postLikeRepository.countByPost(postPS).orElseThrow();
        // Then
        assertEquals(1, postLikeCount);
    }

    // FollowRepository
    @Test
    @Transactional
    public void countByFromUserAndToUserTest(){ // ?????? ?????? ????????? ???, ????????? ?????? ??????
        // Given
        User userPS = userRepository.findByUsername("test").orElseThrow(); // toUser
        User user2PS = userRepository.findByUsername("test2").orElseThrow(); // fromUser
        // When
        long fromUserCount = followRepository.countByFromUser(user2PS).orElseThrow(); // For following count
        long toUserCount = followRepository.countByToUser(userPS).orElseThrow(); // For follower count

        // Then
        assertEquals(1, fromUserCount);
        assertEquals(1, toUserCount);
    }

    // CommentRepository
    @Test
    @Transactional
    public void commentRepoCountByPostTest(){ // Post ??? ?????? ?????? ?????? ??????
        // Given
        Post postPS = postRepository.findAll().get(0);
        // When
        long postCommentCount = commentRepository.countByPost(postPS).orElseThrow();
        // Then
        assertEquals(1, postCommentCount);
    }

    // CommentLikeRepository
    @Test
    @Transactional
    public void countByCommentTest(){ // ?????? ????????? ?????? ??????
        // Given
        Comment commentPS = commentRepository.findAll().get(0);
        // When
        long commentLikeCount = commentLikeRepository.countByComment(commentPS).orElseThrow();
        // Then
        assertEquals(1, commentLikeCount);
    }

    // CommentLikeRepository
    @Test
    @Transactional
    public void findUsersByCommentTest(){ // ????????? ????????? ??? ?????? List ??????
        // Given
        Comment comment = commentRepository.findAll().get(0);
        User userPS = userRepository.findByUsername("test").orElseThrow();
        // When
        List<CommentLikeMapping> userList = commentLikeRepository.findUsersByComment(comment).orElseThrow();
        // Then
        assertEquals(1, userList.size());
        assertEquals(userPS.getUsername(), userList.get(0).getUser().getUsername());
    }


}
