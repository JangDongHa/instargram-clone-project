package com.clone.instargram.dto;

import com.clone.instargram.domain.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseUserDto {
    private String nickname;
    private String email;
    private String description;

    public ResponseUserDto(User user){
        fromUser(user);
    }

    public void fromUser(User user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.description = user.getDescription();
    }
}
