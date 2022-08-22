package com.clone.instargram.web;

import com.clone.instargram.config.jwt.token.RequestToken;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.dto.ResponseUserDto;
import com.clone.instargram.dto.request.RegisterDto;
import com.clone.instargram.dto.request.UpdateUserDto;
import com.clone.instargram.dto.request.UpdateUserProfileDto;
import com.clone.instargram.dto.request.UserDto;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/user/register")
    public ResponseDto<String> registerApi(@RequestBody RegisterDto dto){
        return new ResponseDto<>(HttpStatus.OK, userService.register(dto));
    }

    @PostMapping("/api/user/profile")
    public ResponseDto<String> updateProfileApi(UpdateUserProfileDto dto, HttpServletRequest request){
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
}
