package com.clone.instargram.service;

import com.clone.instargram.dto.request.RegisterDto;
import com.clone.instargram.dto.request.UpdateUserProfileDto;

public interface UserService {
    String register(RegisterDto dto);
    String updateProfileImage(String username, UpdateUserProfileDto dto);
}
