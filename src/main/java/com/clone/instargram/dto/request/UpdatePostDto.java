package com.clone.instargram.dto.request;

import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.tag.Tag;
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

    public Post toPost(Post post, String imageSource){
        return Post.builder()
                .id(post.getId())
                .imageSource(imageSource)
                .description(description)
                .user(post.getUser())
                .createdAt(post.getCreatedAt())
                .likesCount(post.getLikesCount())
                .build();
    }

    public Post toPost(Post post, long likesCount){
        return Post.builder()
                .id(post.getId())
                .imageSource(post.getImageSource())
                .description(post.getDescription())
                .user(post.getUser())
                .createdAt(post.getCreatedAt())
                .likesCount(likesCount)
                .build();
    }

    public Tag toTag(Post post, String tag){
        return Tag.builder()
                .post(post)
                .tagName(tag)
                .build();
    }
}
