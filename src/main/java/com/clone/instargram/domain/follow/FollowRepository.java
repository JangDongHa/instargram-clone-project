package com.clone.instargram.domain.follow;

import com.clone.instargram.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Long> countByFromUser(User fromUser);
    Optional<Long> countByToUser(User toUser);
    Optional<Boolean> existsByToUserAndFromUser(User toUser, User fromUser);
}
