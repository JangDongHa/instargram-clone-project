package com.clone.instargram.web;

import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.dto.user.InfoRequestDto;
import com.clone.instargram.dto.user.SignupRequestDto;
import com.clone.instargram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/api/user/register")
    public ResponseDto signup(@RequestBody SignupRequestDto requestDto) {
           String res = userService.signup(requestDto);
           ResponseDto responseDto = new ResponseDto<>(
                   HttpStatus.OK,
                   res
           );
           return responseDto;
    }

    @PutMapping("/api/user/{id}")
    public Long updateUser(@PathVariable Long id, @RequestBody InfoRequestDto infoRequestDto) {
     return null;
    }
}
