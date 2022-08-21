package com.clone.instargram.dto;

import com.clone.instargram.domain.comment.Comment;
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
    private List<Tag> tags;
    private List<Comment> comments;

    public ResponsePostDto(Post post, List<Tag> tags, List<Comment> comments){
        this.id = post.getId();
        this.imageSource = post.getImageSource();
        this.description = post.getDescription();
        this.likesCount = post.getLikesCount();
        this.tags = tags;
        this.comments = comments;

    }


}
