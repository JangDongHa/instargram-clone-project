package com.clone.instargram.service;

import com.clone.instargram.dto.FollowResponseDto;

import java.util.List;

public interface FollowService {

    // 팔로우 등록, 취소
    String doFollowing(String fromUsername, String toUsername );

    // 팔로워 목록 보기
    List<FollowResponseDto> getFollowers(String toUsername , String myname);

    // 팔로우 목록 보기
    List<FollowResponseDto> getFollows(String fromUsername , String myname);
}
