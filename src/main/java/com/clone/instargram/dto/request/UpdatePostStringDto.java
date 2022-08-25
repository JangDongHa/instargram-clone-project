package com.clone.instargram.dto.request;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.tag.Tag;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePostStringDto {
    private long id;
    private String file;
    private String description;
    private String tag;

    public Post toPost(Post post){
        return Post.builder()
                .imageSource(file)
                .description(description)
                .createdAt(post.getCreatedAt())
                .likesCount(post.getLikesCount())
                .user(post.getUser())
                .id(post.getId())
                .build();
    }


    public Tag toTag(Tag tag){
        return Tag.builder()
                .id(tag.getId())
                .tagName(this.tag)
                .post(tag.getPost())
                .build();
    }
}
