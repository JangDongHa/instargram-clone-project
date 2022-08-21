package com.clone.instargram.dto;

import com.clone.instargram.domain.post.Post;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponsePostListDto {
    private long id;
    private String imageSource;
    private long likesCount;
    private long commentsCount;

    public ResponsePostListDto(Post post, long commentsCount){
        this.id = post.getId();
        this.imageSource = post.getImageSource();
        this.likesCount = post.getLikesCount();
        this.commentsCount =commentsCount;
    }
}
