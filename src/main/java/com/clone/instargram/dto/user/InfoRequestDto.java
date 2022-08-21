package com.clone.instargram.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InfoRequestDto {

    private String username;

    private String password;

    private String email;
}