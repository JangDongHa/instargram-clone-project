package com.clone.instargram.dto;

import com.clone.instargram.domain.comment.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseCommentDto {
    private long id;
    private String nickname;
    private String content;
    private long likesCount;
    private String profileImage;

    private boolean isLiked;

    public ResponseCommentDto(Comment comment, boolean isLiked){
        fromComment(comment, isLiked);
    }

    public void fromComment(Comment comment, boolean isLiked){
        this.id = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.likesCount = comment.getLikesCount();
        this.profileImage = comment.getUser().getProfileImage();

    }
}
