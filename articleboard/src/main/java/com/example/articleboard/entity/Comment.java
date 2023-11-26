package com.example.articleboard.entity;

import com.example.articleboard.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // Exception if there's id in dto
        if(dto.getId()!=null)
            throw new IllegalArgumentException("Failed Comment creation! Id must not exist.");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("Failed Comment craetion! Wrong id of the target post");
            // Create Entity and return
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        if(this.id!=dto.getId())
            throw new IllegalArgumentException("Comment update failed! Wrong id input.");
        if(dto.getNickname()!=null)
            this.nickname = dto.getNickname();
        if(dto.getBody()!=null)
            this.body = dto.getBody();
    }
}
