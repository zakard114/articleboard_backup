package com.example.articleboard.service;

import com.example.articleboard.dto.ArticleForm;
import com.example.articleboard.entity.Article;
import com.example.articleboard.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }



    @Transactional
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId()!=null){
            return null;
        }
        return articleRepository.save(article);
    }

    @Transactional
    public List<Article> createList(List<ArticleForm> dtos) {
        List<Article> articlesList = dtos.stream()
                .map(dto-> dto.toEntity())
                .collect(Collectors.toList());
        articlesList.forEach(article -> articleRepository.save(article));

        return articlesList;
    }


    @Transactional
    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId()!=id){
            log.info("Wrong Request! id: {}, article: {}", id, article.toString());
            return null;
        }
        Article target = articleRepository.findById(id).orElse(null);
        if(target==null){
            log.info("No data correspond to the id:{} at the DB", id);
            return null;
        }
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    @Transactional
    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target==null){
            log.info("Id doesn't exist at DB");
            return null;
        }
        articleRepository.delete(target);
        return target;
    }
}

