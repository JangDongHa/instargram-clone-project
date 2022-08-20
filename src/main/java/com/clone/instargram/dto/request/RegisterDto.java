package com.clone.instargram.dto.request;


import com.clone.instargram.domain.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterDto {
    private String username;
    private String password;
    private String nickname;
    private String email;

    public User toUser(String encryptPassword){
        return User.builder()
                .username(username)
                .password(encryptPassword)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
