package com.clone.instargram.dto.request;

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
    private MultipartFile multipartFile;
    private String description;
    private List<String> tags;
}
