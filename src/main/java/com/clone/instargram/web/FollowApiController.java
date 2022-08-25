package com.clone.instargram.web;

import com.clone.instargram.config.jwt.token.RequestToken;
import com.clone.instargram.dto.FollowResponseDto;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")

public class FollowApiController {

    private final FollowService followService;

    private String getUsername(HttpServletRequest request){
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }

    // 팔로우 등록, 취소
    @PostMapping("/user/{username}/follow")
    public ResponseEntity<ResponseDto<Boolean>> doFollowing(@PathVariable String username , HttpServletRequest request ){
        String fromUser = getUsername( request );
        return ResponseEntity.ok( new ResponseDto<>( HttpStatus.OK , followService.doFollowing( fromUser, username ) ));

    }

    // 팔로워 목록 보기
    @GetMapping("/user/{username}/follower")
    public ResponseEntity<ResponseDto<List<FollowResponseDto>>> getFollowers(@PathVariable String username , HttpServletRequest request ){
        String myname = getUsername( request );
        return ResponseEntity.ok( new ResponseDto<>( HttpStatus.OK , followService.getFollowers( username , myname ) ));
    }

    // 팔로우 목록 보기
    @GetMapping("/user/{username}/follow")
    public ResponseEntity<ResponseDto<List<FollowResponseDto>>> getFollows(@PathVariable String username , HttpServletRequest request ){
        String myname = getUsername( request );
        return ResponseEntity.ok( new ResponseDto<>( HttpStatus.OK , followService.getFollows( username , myname ) ));
    }



}