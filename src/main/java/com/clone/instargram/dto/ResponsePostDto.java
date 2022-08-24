package com.clone.instargram.dto;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.tag.Tag;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponsePostDto {
    private long id;
    private String imageSource;
    private String description;
    private long likesCount;
    private String nickname;
    private String profileImage;
    private List<Tag> tags;
    private List<ResponseCommentDto> comments;

    public ResponsePostDto(Post post, List<Tag> tags, List<ResponseCommentDto> comments){
        this.id = post.getId();
        this.imageSource = post.getImageSource();
        this.description = post.getDescription();
        this.likesCount = post.getLikesCount();
        this.nickname = post.getUser().getNickname();
        this.profileImage = post.getUser().getProfileImage();
        this.tags = tags;
        this.comments = comments;

    }



}
