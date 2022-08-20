package com.clone.instargram.service.impl;

import com.clone.instargram.domain.follow.FollowRepository;
import com.clone.instargram.domain.post.PostRepository;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.FeedProfileDto;
import com.clone.instargram.exception.definition.ExceptionNaming;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 피드 회원정보 조회
    @Override
    public FeedProfileDto getFeedProfile(String username) {

        User user = userRepository.findByUsername( username ).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NOT_FOUND_USER )
        );

        long postsCount = postRepository.countByUser( user ).orElse( 0L );
        long followerCount = followRepository.countByToUser( user ).orElse( 0L );
        long followCount = followRepository.countByFromUser( user ).orElse( 0L );

        return new FeedProfileDto( user, postsCount , followerCount, followCount );
    }

}
