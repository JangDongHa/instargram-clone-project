package com.clone.instargram.service.impl;

import com.clone.instargram.domain.follow.Follow;
import com.clone.instargram.domain.follow.FollowRepository;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.FollowResponseDto;
import com.clone.instargram.exception.definition.ExceptionNaming;
import com.clone.instargram.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {

    private final UserRepository userRepository;

    private final FollowRepository followRepository;

    // 팔로우 등록, 취소
    @Override
    @Transactional
    public String doFollowing(String fromUsername, String toUsername ) {

        User fromUser = userRepository.findByUsername( fromUsername ).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NOT_FOUND_USER )
        );
        User toUser = userRepository.findByUsername( toUsername ).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NOT_FOUND_USER )
        );

        if( fromUser == toUser ){
            throw new DataIntegrityViolationException( ExceptionNaming.WRONG_TYPE );
        }

        boolean isFollowed = followRepository.existsByToUserAndFromUser( toUser , fromUser ).orElseThrow();

        if( isFollowed ){
            followRepository.deleteByToUserAndFromUser( toUser , fromUser );
            return "팔로우 취소";
        }

        Follow follow = Follow.builder().fromUser( fromUser ).toUser( toUser ).build();
        followRepository.save( follow );
        return "팔로우 완료";
   }

   // 팔로워 목록 보기
    @Override
    public List<FollowResponseDto> getFollowers(String toUsername, String myname) {

        User toUser = userRepository.findByUsername( toUsername ).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NOT_FOUND_USER )
        );
        User me = userRepository.findByUsername( myname ).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NOT_FOUND_USER )
        );

        List<Follow> followers = followRepository.findByToUser( toUser );
        List<FollowResponseDto> follower_list = new ArrayList<>();

        for( Follow follower : followers ){
            User fromUser = follower.getFromUser();
            boolean isFollowed = followRepository.existsByToUserAndFromUser( fromUser , me ).orElseThrow();
            follower_list.add( new FollowResponseDto( fromUser, isFollowed ) );
        }

        return follower_list;
    }

    // 팔로우 목록 보기
    @Override
    public List<FollowResponseDto> getFollows(String fromUsername, String myname) {
        User fromUser = userRepository.findByUsername( fromUsername ).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NOT_FOUND_USER )
        );
        User me = userRepository.findByUsername( myname ).orElseThrow(
                () -> new UsernameNotFoundException(ExceptionNaming.NOT_FOUND_USER )
        );

        List<Follow> follows = followRepository.findByFromUser( fromUser );
        List<FollowResponseDto> follow_list = new ArrayList<>();

        for( Follow follow : follows ){
            User toUser = follow.getToUser();
            boolean isFollowed = followRepository.existsByToUserAndFromUser( toUser , me ).orElseThrow();
            follow_list.add( new FollowResponseDto( toUser, isFollowed ) );
        }

        return follow_list;
    }


}
