package com.clone.instargram.service;

import com.clone.instargram.dto.request.CommentDto;
import com.clone.instargram.dto.ResponseDto;


public interface CommentService {
    ResponseDto<?> commentCreate(Long postId, CommentDto requestDto, String username);

    ResponseDto<?> commentUpdate(Long postId, Long commentId, CommentDto request, String username);

    ResponseDto<?> commentDelete(Long postId, Long commentId, String username);

    ResponseDto<?> commentLike(Long postId, Long commentId, String username);
//
    ResponseDto<?> commentLikeInfo(Long postId, Long commentId);
}
