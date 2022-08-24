package com.clone.instargram.config.jwt.token.properties;

public interface AccessTokenProperties extends CommonTokenProperties{
    int EXPIRE_TIME = 1000 * 60 * 3000; // 만료시간(1000 = 1초) : 현재시간 + 30분 (현재는 3000분)
    String HEADER_STRING = "Authorization";
}