package com.clone.instargram.domain.post;

import com.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Long> countByUser(User user);
}
