package com.clone.instargram.web;

import com.clone.instargram.config.jwt.token.RequestToken;
import com.clone.instargram.dto.request.CommentDto;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    // 댓글 작성 - 로그인 필요
    @PostMapping("/api/user/posts/{postId}/comments")
    public ResponseDto<?> createComment(@PathVariable Long postId,
                                        @RequestBody CommentDto requestDto,
                                        HttpServletRequest request
                                        ){
        return commentService.commentCreate(postId, requestDto, getUsername(request));
    }

    // 댓글 수정 - 로그인 필요
    @PutMapping("/api/user/posts/{postId}/comments/{commentId}")
    public ResponseDto<?> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                        @RequestBody CommentDto requestDto,  HttpServletRequest request){
        return commentService.commentUpdate(postId, commentId, requestDto, getUsername(request));
    }

    // 댓글 삭제 - 로그인 필요
    @DeleteMapping("/api/user/posts/{postId}/comments/{commentId}")
    public ResponseDto<?> deleteComment(@PathVariable Long postId, @PathVariable Long commentId,
                                       HttpServletRequest request){
        return commentService.commentDelete(postId, commentId, getUsername(request));
    }

    // 댓글 좋아요 & 취소 - 로그인 필요
    @PostMapping("/api/user/posts/{postId}/comments/{commentId}/likes")
    public ResponseDto<?> likeComment(@PathVariable Long postId, @PathVariable Long commentId,
                                      HttpServletRequest request){
        return commentService.commentLike(postId, commentId, getUsername(request));
    }

    // 댓글 좋아요 상세보기
    @GetMapping("api/user/posts/{postId}/comments/{commentId}/likes")
    public ResponseDto<?> commentLikeInfo(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        return commentService.commentLikeInfo(postId, commentId, getUsername(request));
    }

    // 로그인 검증, 로그인한 유저의 username 가지고 옴
    private String getUsername(HttpServletRequest request){
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }
}
