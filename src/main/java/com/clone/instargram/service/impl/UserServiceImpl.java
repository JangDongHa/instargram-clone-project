package com.clone.instargram.service.impl;

import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.user.InfoRequestDto;
import com.clone.instargram.dto.user.SignupRequestDto;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String signup(SignupRequestDto requestDto) {

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .email(requestDto.getEmail())
                .build();
        userRepository.save(user);
        return "회원가입 완료";
    }
}
