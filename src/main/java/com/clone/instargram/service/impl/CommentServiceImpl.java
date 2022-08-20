package com.clone.instargram.service.impl;

import com.clone.instargram.domain.comment.Comment;
import com.clone.instargram.domain.comment.CommentRepository;
import com.clone.instargram.domain.comment.like.CommentLike;
import com.clone.instargram.domain.comment.like.CommentLikeMapping;
import com.clone.instargram.domain.comment.like.CommentLikeRepository;
import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.post.PostRepository;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.CommentLikeInfoResponseDto;
import com.clone.instargram.dto.Request.CommentDto;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.exception.definition.ExceptionNaming;
import com.clone.instargram.service.CommentService;
import com.clone.instargram.web.CommentApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    PostRepository postRepository;
    CommentRepository commentRepository;
    CommentLikeRepository commentLikeRepository;
    UserRepository userRepository;


    // 댓글 작성
    @Override
    public ResponseDto<?> commentCreate(Long postId, CommentDto requestDto,
                                        String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NEED_TOKEN)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_POST)
        );
        Comment comment = Comment.builder()
//                .user(user)
                .post(post)
                .content(requestDto.getContent())
                .build();
        commentRepository.save(comment);
        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .data("댓글 작성이 완료되었습니다.")
                .build();
    }

    // 댓글 수정
    @Override
    public ResponseDto<?> commentUpdate(Long postId, Long commentId,
                                        CommentDto requestDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NEED_TOKEN)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_POST)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_COMMENT)
        );
        if (comment.validateUser(user)) {
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data("작성자만 수정할 수 있습니다.")
                    .build();
        }
        comment.update(requestDto);
        commentRepository.save(comment);
        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .data("댓글 수정이 완료되었습니다.")
                .build();
    }

    // 댓글 삭제
    @Override
    public ResponseDto<?> commentDelete(Long postId, Long commentId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NEED_TOKEN)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_POST)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_COMMENT)
        );
        if (comment.validateUser(user)) {
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data("작성자만 삭제할 수 있습니다.")
                    .build();
        }
        commentRepository.delete(comment);
        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .data("댓글 삭제가 완료되었습니다.")
                .build();
    }

    // 댓글 좋아요 & 취소
    @Override
    public ResponseDto<?> commentLike(Long postId, Long commentId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NEED_TOKEN)
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_POST)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_COMMENT)
        );
        if (!commentLikeRepository.findCommentLikeByUserAndComment(user, comment)) {
            commentLikeRepository.save(
                    CommentLike.builder()
                            .user(user)
                            .comment(comment)
                            .build()
            );
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .data("좋아요 완료")
                    .build();
        } else {
            commentLikeRepository.delete(
                    CommentLike.builder()
                            .user(user)
                            .comment(comment)
                            .build()
            );
            return ResponseDto.builder()
                    .httpStatus(HttpStatus.OK)
                    .data("좋아요 취소 완료")
                    .build();
        }
    }


    // 댓글 좋아요 상세보기
    public ResponseDto<?> commentLikeInfo(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_POST)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException(ExceptionNaming.CANNOT_FIND_COMMENT)
        );
        List<CommentLike> commentLikeList = commentLikeRepository.findByCommentId(commentId);
        List<CommentLikeInfoResponseDto> commentLikeInfoResponseDtoList = new ArrayList<>();
        for (CommentLike commentLike : commentLikeList) {
            commentLikeInfoResponseDtoList.add(
                    CommentLikeInfoResponseDto.builder()
                            .username(commentLike.getUser().getUsername())
//                            .nickname(commentLike.getUser().getNickname())
                            // 유저 엔티티에 닉네임 없고 ERD에도 없음
                            .profileImage(commentLike.getUser().getProfileImage())
                            .build()
            );
        }
        return ResponseDto.builder()
                .httpStatus(HttpStatus.OK)
                .data(commentLikeInfoResponseDtoList)
                .build();
    }
}


