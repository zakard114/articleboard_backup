package com.example.articleboard.service;

import com.example.articleboard.dto.CommentDto;
import com.example.articleboard.entity.Article;
import com.example.articleboard.entity.Comment;
import com.example.articleboard.repository.ArticleRepository;
import com.example.articleboard.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long articleId) {

        List<Comment> comments = commentRepository.findByArticleId(articleId);
        List<CommentDto> dtos = comments.stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
        return dtos;
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // Check if the article id ever exists, otherwise call the illegalArgumentException
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("Create Fail! " +
                        "No such Article exists"));
        // Switch Dto to Entity
        Comment comment = Comment.createComment(dto, article);
        // save into DB via repository
        Comment created = commentRepository.save(comment);

        // return a List<CommentDto> object
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        Comment target = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("The Id not exist at DB"));
        target.patch(dto);
        Comment updated = commentRepository.save(target);
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("The Id not exist at DB"));
        commentRepository.delete(comment);

        return CommentDto.createCommentDto(comment);
    }
}
