package com.clone.instargram.service;

import com.clone.instargram.dto.FeedProfileDto;

public interface UserService {

    // 피드 회원정보 보기
    FeedProfileDto getFeedProfile(String username);

}
