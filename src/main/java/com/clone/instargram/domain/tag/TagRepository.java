package com.clone.instargram.domain.tag;

import com.clone.instargram.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    void deleteAllByPost(Post post);
    Optional<List<Tag>> findAllByPost(Post post);
}
