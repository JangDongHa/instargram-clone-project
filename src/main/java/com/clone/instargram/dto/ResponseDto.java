package com.clone.instargram.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ResponseDto<T> {
    HttpStatus httpStatus;
    T data;
}
