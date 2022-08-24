package com.clone.instargram.dto;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.tag.Tag;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponsePostRecentListDto {
    private long id;
    private String imageSource;
    private long likesCount;
    private long commentsCount;
    private String description;
    private Tag tag;
    private String nickname;
    private String username;
    private String profileImage;

    private boolean isLiked;

    public ResponsePostRecentListDto(Post post, long commentsCount, Tag tag, boolean isLiked){
        this.id = post.getId();
        this.imageSource = post.getImageSource();
        this.likesCount = post.getLikesCount();
        this.commentsCount =commentsCount;
        this.description = post.getDescription();
        this.tag = tag;
        this.nickname = post.getUser().getNickname();
        this.username = post.getUser().getUsername();
        this.profileImage = post.getUser().getProfileImage();
        this.isLiked = isLiked;
    }

}
