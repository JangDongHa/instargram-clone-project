package com.clone.instargram.web;

import com.clone.instargram.config.jwt.token.RequestToken;
import com.clone.instargram.dto.*;
import com.clone.instargram.dto.request.PostStringDto;
import com.clone.instargram.dto.request.UpdatePostStringDto;
import com.clone.instargram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final PostService postService;
    @PostMapping("/api/user/posts")
    public ResponseDto<String> createPostApi(@RequestBody PostStringDto postDto, HttpServletRequest request){
        return new ResponseDto<>(HttpStatus.OK, postService.createPost(postDto, getUsername(request)));
    }

    @PutMapping("/api/user/posts/{postId}")
    public ResponseDto<String> updatePostApi(@PathVariable long postId, HttpServletRequest request, @RequestBody UpdatePostStringDto updatePostDto){
        updatePostDto.setId(postId);
        return new ResponseDto<>(HttpStatus.OK, postService.updatePost(updatePostDto, getUsername(request)));
    }

    @GetMapping("/api/user/posts/{postId}")
    public ResponseDto<ResponsePostDto> getPostApi(@PathVariable long postId){
        return new ResponseDto<>(HttpStatus.OK, postService.getPost(postId));
    }

    @DeleteMapping("/api/user/posts/{postId}")
    public ResponseDto<String> deletePostApi(@PathVariable long postId, HttpServletRequest request) {
        return new ResponseDto<>(HttpStatus.OK, postService.deletePost(postId, getUsername(request)));
    }

    @GetMapping("/api/user/posts/{postId}/likes")
    public ResponseDto<List<ResponsePostLikeUserDto>> getPostLikeUsersApi(@PathVariable long postId){
        return new ResponseDto<>(HttpStatus.OK, postService.getPostLikeUsers(postId));
    }

    @PostMapping("/api/user/posts/{postId}/likes")
    public ResponseDto<Boolean> createPostLikeApi(@PathVariable long postId, HttpServletRequest request){
        return new ResponseDto<>(HttpStatus.OK, postService.postLike(postId, getUsername(request)));
    }

    @GetMapping("/api/user/{username}/posts")
    public ResponseDto<List<ResponsePostListDto>> getPostListApi(@PathVariable String username){
        return new ResponseDto<>(HttpStatus.OK, postService.getPostList(username));
    }

    @GetMapping("/api/recent/posts")
    public ResponseDto<Page<ResponsePostRecentListDto>> getRecentPostListApi(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    , HttpServletRequest request){
        return new ResponseDto<>(HttpStatus.OK, postService.getRecentPostList(pageable, getUsername(request)));
    }


    private String getUsername(HttpServletRequest request){
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }


}
