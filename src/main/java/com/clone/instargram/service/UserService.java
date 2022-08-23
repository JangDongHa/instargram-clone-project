package com.clone.instargram.service;

import com.clone.instargram.dto.FeedProfileDto;
import com.clone.instargram.dto.ResponseUserDto;
import com.clone.instargram.dto.request.RegisterDto;
import com.clone.instargram.dto.request.UpdateUserDto;
import com.clone.instargram.dto.request.UpdateUserProfileStringDto;
import com.clone.instargram.dto.request.UserDto;

public interface UserService {
    String register(RegisterDto dto);
    String updateProfileImage(String username, UpdateUserProfileStringDto dto);
    ResponseUserDto checkUpdateUser(UserDto dto, String username);
    String updateUser(UpdateUserDto dto, String username);
    // 피드 회원정보 보기
    FeedProfileDto getFeedProfile(String username);
}
