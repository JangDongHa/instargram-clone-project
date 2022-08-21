package com.clone.instargram.web;

import com.clone.instargram.config.jwt.token.RequestToken;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.dto.ResponsePostDto;
import com.clone.instargram.dto.request.PostDto;
import com.clone.instargram.dto.request.UpdatePostDto;
import com.clone.instargram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    @PostMapping("/api/user/posts")
    public ResponseDto<String> createPostApi(PostDto postDto, HttpServletRequest request){
        return new ResponseDto<>(HttpStatus.OK, postService.createPost(postDto, getUsername(request)));
    }

    @PutMapping("/api/user/posts/{postId}")
    public ResponseDto<String> updatePostApi(@PathVariable long postId, HttpServletRequest request, UpdatePostDto updatePostDto){
        updatePostDto.setId(postId);
        return new ResponseDto<>(HttpStatus.OK, postService.updatePost(updatePostDto, getUsername(request)));
    }

    @GetMapping("/api/user/posts/{postId}")
    public ResponseDto<ResponsePostDto> getPostApi(@PathVariable long postId){
        return new ResponseDto<>(HttpStatus.OK, postService.getPost(postId));
    }


    private String getUsername(HttpServletRequest request){
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }


}
