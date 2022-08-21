package com.clone.instargram.service;

import com.clone.instargram.dto.ResponsePostDto;
import com.clone.instargram.dto.request.PostDto;
import com.clone.instargram.dto.request.UpdatePostDto;

public interface PostService {
    String createPost(PostDto postDto, String usernameTK);
    String updatePost(UpdatePostDto postDto, String usernameTK);
    ResponsePostDto getPost(long postId);
    String deletePost(long postId, String usernameTK);
}
