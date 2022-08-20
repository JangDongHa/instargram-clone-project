package com.clone.instargram.service.impl;

import com.clone.instargram.domain.user.User;
import com.clone.instargram.domain.user.UserRepository;
import com.clone.instargram.dto.request.RegisterDto;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public String register(RegisterDto dto){
        User user = dto.toUser(bCryptPasswordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return "회원가입 완료";
    }
}
