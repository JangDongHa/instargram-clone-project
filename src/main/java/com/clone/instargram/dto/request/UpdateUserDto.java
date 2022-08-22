package com.clone.instargram.dto.request;

import com.clone.instargram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDto {
    private String nickname;
    private String email;
    private String password;
    private String description;


    public User toUser(User user){
        return User.builder()
                .id(user.getId())
                .nickname(nickname)
                .email(email)
                .password(password)
                .description(description)
                .profileImage(user.getProfileImage())
                .createdAt(user.getCreatedAt())
                .username(user.getUsername())
                .build();
    }

    public boolean isNull(){
        if (nickname == null || email == null ||
            password == null || description == null)
            return true;
        return false;
    }
}
