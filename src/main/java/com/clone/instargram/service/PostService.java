package com.clone.instargram.service;

import com.clone.instargram.dto.ResponsePostDto;
import com.clone.instargram.dto.ResponsePostLikeUserDto;
import com.clone.instargram.dto.request.PostDto;
import com.clone.instargram.dto.request.UpdatePostDto;

import java.util.List;

public interface PostService {
    String createPost(PostDto postDto, String usernameTK);
    String updatePost(UpdatePostDto postDto, String usernameTK);
    ResponsePostDto getPost(long postId);
    String deletePost(long postId, String usernameTK);
    List<ResponsePostLikeUserDto> getPostLikeUsers(long postId);
    String postLike(long postId, String usernameTK);
}
