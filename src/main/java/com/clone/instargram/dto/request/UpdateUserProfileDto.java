package com.clone.instargram.dto.request;

import com.clone.instargram.domain.user.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserProfileDto {
    private MultipartFile file;

    public User toUser(User user, String imageSource){
        return User.builder()
                .id(user.getId())
                .description(user.getDescription())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .profileImage(imageSource)
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
