package com.clone.instargram.dto;

import com.clone.instargram.domain.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponsePostLikeUserDto {
    private String username;
    private String nickname;
    private String profileImage;

    public ResponsePostLikeUserDto(User user){
        fromUser(user);
    }

    public void fromUser(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }
}
