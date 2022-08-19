package com.clone.instargram.exception;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.clone.instargram.dto.ResponseDto;
import com.clone.instargram.exception.definition.ExceptionNaming;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseDto<String> proceedAllException(Exception e){
        return new ResponseDto<>(HttpStatus.EXPECTATION_FAILED, e.getMessage());
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseDto<String> tokenExpiredException(TokenExpiredException e){
        return new ResponseDto<>(HttpStatus.UNAUTHORIZED, ExceptionNaming.TOKEN_EXPIRED);
    }

    @ExceptionHandler(value = SignatureVerificationException.class)
    public ResponseDto<String> signatureVerificationException(SignatureVerificationException e){
        return new ResponseDto<>(HttpStatus.BAD_REQUEST, ExceptionNaming.BAD_SIGNITURE);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseDto<String> nullPointerException(NullPointerException e){
        final String TOKEN_NULL_EXCEPTION_MESSAGE = "http.HttpServletRequest.getHeader(String)";
        if (e.getMessage().contains(TOKEN_NULL_EXCEPTION_MESSAGE)) // 로그인이 필요한 서비스에 토큰을 입력하지 않은 경우
            return new ResponseDto<>(HttpStatus.UNAUTHORIZED, ExceptionNaming.NEED_TOKEN);

        return new ResponseDto<>(HttpStatus.EXPECTATION_FAILED, ExceptionNaming.NULL_EXCEPTION);
    }

    @ExceptionHandler(value = SecurityException.class)
    public ResponseDto<String> passwordInconsistency(SecurityException e){
        return new ResponseDto<>(HttpStatus.NOT_ACCEPTABLE, ExceptionNaming.PASSWORD_INCONSISTENCY);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseDto<String> dataIntegrityViolationException(DataIntegrityViolationException e){
        return new ResponseDto<>(HttpStatus.CONFLICT, ExceptionNaming.WRONG_TYPE);
    }
}
