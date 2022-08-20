package com.clone.instargram.web;

import com.clone.instargram.dto.FeedProfileDto;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;


    // 피드 회원정보 보기
    @GetMapping("/api/user/{username}")
    public ResponseEntity<ResponseDto<FeedProfileDto>> getFeedProfile(@PathVariable String username ){
        return ResponseEntity.ok( new ResponseDto<>( HttpStatus.OK , userService.getFeedProfile( username ) ));
    }
}
