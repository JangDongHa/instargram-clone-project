package com.clone.instargram.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequestDto {

    private String username;

    private String password;

    private String email;

    private String nickname;
}
