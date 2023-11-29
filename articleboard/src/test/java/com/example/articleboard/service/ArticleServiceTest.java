package com.example.articleboard.service;

import com.example.articleboard.dto.ArticleForm;
import com.example.articleboard.entity.Article;
import com.example.articleboard.repository.ArticleRepository;
import com.example.articleboard.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 1. Expect data
        Article a = new Article(1L, "aaaa", "1111");
        Article b = new Article(2L, "bbbb", "2222");
        Article c = new Article(3L, "cccc", "3333");
        Article d = new Article(4L, "What is your favorite movie?", "Comment Go");
        Article e = new Article(5L, "What is your soul food?", "Comment Go Go");
        Article f = new Article(6L, "What is your hobby?", "Comment Go Go Go");

        List<Article> expected = new ArrayList<>(Arrays.asList(a, b, c, d, e, f));

        // 2. Actual data
        List<Article> articles = articleService.index();
        // 3. Compare and verify
        assertEquals(expected.toString(), articles.toString());

    }

    @Test
    void show_with_exist_id() {
        Long id = 1L;
        Article expected = new Article(id, "aaaa", "1111");
        Article article = articleService.show(id);
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_with_wrong_id() {
        Long id = -1L;
        Article expected = null;
        Article article = articleService.show(id);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_success_without_id() {
        String title = "zzzz";
        String content = "7777";
        ArticleForm dto = new ArticleForm(null, title, content);

        Article expected = new Article(7L, title, content);
        Article created = articleService.create(dto);
        assertEquals(expected.toString(), created.toString());
    }
    @Test
    @Transactional
    void create_fail_with_id() {
        Long id = 4L;
        String title = "dddd";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);

        Article expected = null;
        Article created = articleService.create(dto);
        assertEquals(expected, created);
    }

    @Test
    @DisplayName("Update Successful - dto input with existed Id, title, and content")
    @Transactional
    void update() {
        // 1. Set id and dto
        Long id = 1L;
        String title = "7777";
        String content = "7L7L7L";
        ArticleForm dto = new ArticleForm(id, title, content);
        // 2. Expected data
        Article expected = new Article(1L, "7777", "7L7L7L");
        // 3. Actual data
        Article actual = articleService.update(id, dto);
        // 4. Compare and verify
        assertEquals(expected.toString(), actual.toString());

    }


    @Test
    @DisplayName("Update Successful - dto input with existed Id & title only")
    @Transactional
    void update2() {
        // 1. Set id and title
        Long id = 1L;
        String title = "7777";
        String content = null;
        ArticleForm dto = new ArticleForm(id, title, content);
        // 2. Expected
        Article expected = new Article(1L, "7777", "1111");
        // 3. Actual
        Article actual = articleService.update(id, dto);
        // 4. Compare and verify
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    @DisplayName("Update Fail - dto input with non-exist Id")
    @Transactional
    void update3() {
        // 1. Set non-exist Id
        Long id = -1L;
        String title = "7777";
        String content = "7L7L7L";
        ArticleForm dto = new ArticleForm(id, title, content);
        // 2. Expected
        Article expected = null;
        // 3. Actual
        Article actual = articleService.update(id, dto);
        // 4. Compare and verify
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete success_Existed_id")
    @Transactional
    void delete1() {
        // 1. Set resources
        Long id = 1L;
        // 2. Expected
        Article expected = new Article(1L, "aaaa", "1111");
        // 3. Actual
        Article deleted = articleService.delete(id);
        // 4. Compare and verify
        assertEquals(expected.toString(), deleted.toString());
    }

    @Test
    @DisplayName("Delete failed_Non-existed_id")
    @Transactional
    void delete2() {
        // 1. Set resources
        Long id = -1L;
        // 2. Expected
        Article expected = null;
        // 3. Actual
        Article deleted = articleService.delete(id);
        // 4. Compare and verify
        assertEquals(expected, deleted);
    }
}