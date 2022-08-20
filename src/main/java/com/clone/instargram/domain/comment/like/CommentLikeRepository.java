package com.clone.instargram.domain.comment.like;

import com.clone.instargram.domain.comment.Comment;
import com.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<Long> countByComment(Comment comment);
    Optional<List<CommentLikeMapping>> findUsersByComment(Comment comment);
    boolean findCommentLikeByUserAndComment(User user, Comment comment);

    List<CommentLike> findByCommentId(Long commentId);
}
