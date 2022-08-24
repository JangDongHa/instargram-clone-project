package com.clone.instargram.dto.request;

import com.clone.instargram.domain.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserProfileStringDto {
    private String file;
    private String description;

    public User toUser(User user){
        return User.builder()
                .id(user.getId())
                .description(description)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .profileImage(file)
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
