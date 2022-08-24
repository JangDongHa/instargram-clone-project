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

    private boolean isFollowed;

    public ResponsePostLikeUserDto(User user, boolean isFollowed){
        fromUser(user, isFollowed);
    }

    public void fromUser(User user, boolean isFollowed){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
        this.isFollowed = isFollowed;
    }
}
