package com.clone.instargram.dto;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.tag.Tag;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponsePostUserListDto {
    private long id;
    private String imageSource;
    private long likesCount;
    private long commentsCount;
    private String description;
    private Tag tag;

    public ResponsePostUserListDto(Post post, long commentsCount, Tag tag){
        this.id = post.getId();
        this.imageSource = post.getImageSource();
        this.likesCount = post.getLikesCount();
        this.commentsCount =commentsCount;
        this.description = post.getDescription();
        this.tag = tag;
    }
}
