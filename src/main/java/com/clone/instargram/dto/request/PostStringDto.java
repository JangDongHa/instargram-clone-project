package com.clone.instargram.dto.request;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.tag.Tag;
import com.clone.instargram.domain.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostStringDto {
    private String file;
    private String description;
    private String tag;

    public Post toPost(User user){
        return Post.builder()
                .user(user)
                .description(description)
                .imageSource(file)
                .build();
    }


    public Tag toTag(Post post){
        return Tag.builder()
                .tagName(tag)
                .post(post)
                .build();
    }
}
