package com.clone.instargram.service;

import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.dto.user.InfoRequestDto;
import com.clone.instargram.dto.user.SignupRequestDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService {
        public String signup(SignupRequestDto requestDto);

}
