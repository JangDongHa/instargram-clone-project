package com.clone.instargram.service.impl;

import com.clone.instargram.domain.post.PostRepository;
import com.clone.instargram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public String createPost(){
        return "";
    }
}
