package com.clone.instargram.domain.post.like;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<Long> countByPost(Post post);

//    @Query(value = "SELECT user_id FROM postlike WHERE post_id=:post_id", nativeQuery = true)
    Optional<List<PostLikeMapping>> findUsersByPost(Post post);
    Boolean existsByUserAndPost(User user, Post post);
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
