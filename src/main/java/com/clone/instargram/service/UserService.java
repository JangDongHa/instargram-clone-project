package com.clone.instargram.service;

import com.clone.instargram.dto.request.RegisterDto;

public interface UserService {
    String register(RegisterDto dto);
}
