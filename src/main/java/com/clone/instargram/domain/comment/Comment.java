package com.clone.instargram.domain.comment;

import com.clone.instargram.domain.common.Timestamped;
import com.clone.instargram.domain.post.Post;
import com.clone.instargram.domain.user.User;
import com.clone.instargram.dto.Request.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private User user;

    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    private Post post;

    @Column(nullable = false)
    private String content;

    // 댓글 수정시 사용
    public void update(CommentDto requestDto) {
        this.content = requestDto.getContent();
    }

    public boolean validateUser(User user) {
        return !this.user.getUsername().equals(user.getUsername());
    }
}
