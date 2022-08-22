package com.clone.instargram.service;

import com.clone.instargram.dto.ResponseUserDto;
import com.clone.instargram.dto.request.RegisterDto;
import com.clone.instargram.dto.request.UpdateUserDto;
import com.clone.instargram.dto.request.UpdateUserProfileDto;
import com.clone.instargram.dto.request.UserDto;
import com.clone.instargram.dto.FeedProfileDto;

public interface UserService {
    String register(RegisterDto dto);
    String updateProfileImage(String username, UpdateUserProfileDto dto);
    ResponseUserDto checkUpdateUser(UserDto dto, String username);
    String updateUser(UpdateUserDto dto, String username);
    // 피드 회원정보 보기
    FeedProfileDto getFeedProfile(String username);
}
