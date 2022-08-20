package com.clone.instargram.web;

import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.dto.request.RegisterDto;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/user/register")
    public ResponseDto<String> registerApi(@RequestBody RegisterDto dto){
        return new ResponseDto<>(HttpStatus.OK, userService.register(dto));
    }
}
