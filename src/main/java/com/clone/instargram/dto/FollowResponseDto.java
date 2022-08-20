package com.clone.instargram.dto;

import com.clone.instargram.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowResponseDto {

    private String username;
    private String nickname;
    private String profileImage;
    private boolean isFollowed;

    public FollowResponseDto(User user , boolean isFollowed ){
        username = user.getUsername();
        nickname = user.getNickname();
        profileImage = user.getProfileImage();
        this.isFollowed = isFollowed;
    }


}
