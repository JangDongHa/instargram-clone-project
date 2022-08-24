package com.clone.instargram.service;

import com.clone.instargram.dto.ResponsePostRecentListDto;
import com.clone.instargram.dto.request.PostStringDto;
import com.clone.instargram.dto.request.UpdatePostStringDto;
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
    Boolean postLike(long postId, String usernameTK);
    List<ResponsePostListDto> getPostList(String username);
    Page<ResponsePostRecentListDto> getRecentPostList(Pageable pageable);
}
