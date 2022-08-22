package com.clone.instargram.dto.request;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.tag.Tag;
import com.clone.instargram.domain.user.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PostDto {
    private MultipartFile file;
    private String description;
    private List<String> tags;
    private String imageSource;

    public Post toPost(User user){
        return Post.builder()
                .imageSource(imageSource)
                .description(description)
                .user(user)
                .build();
    }

    public Tag toTag(Post post, String tag){
        return Tag.builder()
                .post(post)
                .tagName(tag)
                .build();
    }
}
