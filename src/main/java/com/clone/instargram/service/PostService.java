package com.clone.instargram.service;

import com.clone.instargram.dto.Request.PostStringDto;
import com.clone.instargram.dto.Request.UpdatePostStringDto;
import com.clone.instargram.dto.ResponsePostDto;
import com.clone.instargram.dto.ResponsePostLikeUserDto;
import com.clone.instargram.dto.ResponsePostListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    String createPost(PostStringDto postDto, String usernameTK);
    String updatePost(UpdatePostStringDto postDto, String usernameTK);
    ResponsePostDto getPost(long postId);
    String deletePost(long postId, String usernameTK);
    List<ResponsePostLikeUserDto> getPostLikeUsers(long postId);
    String postLike(long postId, String usernameTK);
    List<ResponsePostListDto> getPostList(String username);
    Page<ResponsePostListDto> getRecentPostList(Pageable pageable);
}
