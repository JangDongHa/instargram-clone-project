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
public class UpdatePostDto {
    private long id;
    private MultipartFile file;
    private String description;
    private List<String> tags;

    public Post toPost(User user, String imageSource){
        return Post.builder()
                .id(id)
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
