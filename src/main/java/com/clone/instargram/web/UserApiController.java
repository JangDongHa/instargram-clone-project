package com.clone.instargram.web;

import com.clone.instargram.config.jwt.token.RequestToken;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.dto.ResponseUserDto;
import com.clone.instargram.dto.request.*;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.clone.instargram.dto.FeedProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/user/register")
    public ResponseDto<String> registerApi(@RequestBody RegisterDto dto){
        return new ResponseDto<>(HttpStatus.OK, userService.register(dto));
    }

    @PutMapping("/api/user/profile")
    public ResponseDto<String> updateProfileApi(@RequestBody UpdateUserProfileStringDto dto, HttpServletRequest request){
        return new ResponseDto<>(HttpStatus.OK, userService.updateProfileImage(getUsername(request), dto));
    }

    @PostMapping("/api/user")
    public ResponseDto<ResponseUserDto> checkUpdateApi(@RequestBody UserDto dto, HttpServletRequest request){
        return new ResponseDto<>(HttpStatus.OK, userService.checkUpdateUser(dto, getUsername(request)));
    }

    @PutMapping("/api/user")
    public ResponseDto<String> updateApi(@RequestBody UpdateUserDto dto, HttpServletRequest request){
        return new ResponseDto<>(HttpStatus.OK, userService.updateUser(dto, getUsername(request)));
    }

    private String getUsername(HttpServletRequest request){
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }

    // 피드 회원정보 보기
    @GetMapping("/api/user/{username}")
    public ResponseEntity<ResponseDto<FeedProfileDto>> getFeedProfile(@PathVariable String username ){
        return ResponseEntity.ok( new ResponseDto<>( HttpStatus.OK , userService.getFeedProfile( username ) ));
    }
}
