package com.clone.instargram.domain.comment;

import com.clone.instargram.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Long> countByPost(Post post);
    Optional<List<Comment>> findAllByPost(Post post);
}
