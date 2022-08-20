package com.clone.instargram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeInfoResponseDto {
    private String username;
//    private String nickname;
    private String profileImage;
}
