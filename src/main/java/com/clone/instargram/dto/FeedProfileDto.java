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
public class FeedProfileDto {

    private String username;
    private String nickname;
    private String description;
    private String profileImage;
    private Long postsCount;
    private Long followerCount;
    private Long followCount;


    public FeedProfileDto(User user , long postsCount , long followerCount , long followCount ){
        username = user.getUsername();
        nickname = user.getNickname();
        description = user.getDescription();
        profileImage = user.getProfileImage();
        this.postsCount = postsCount;
        this.followCount = followCount;
        this.followerCount = followerCount;
    }


}