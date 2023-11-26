package com.example.articleboard.repository;

import com.example.articleboard.entity.Article;
import com.example.articleboard.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("Searching for all comments of a post")
    void findByArticleId() {
        // 1. Prepare input data
        Long articleId = 4L;
        // 2. Actual data
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        // 3. Expected data
        Article article = new Article(4L, "What is your favorite movie?", "Comment Go");
        Comment a = new Comment(1L, article, "Park", "Good Will Hunting");
        Comment b = new Comment(2L, article, "Kim", "I am Sam");
        Comment c = new Comment(3L, article, "Choi", "Shawshank Redemption");
        List<Comment> expected = Arrays.asList(a, b, c);
        // 4. Compare and Verify
        assertEquals(expected.toString(), comments.toString());
    }

    @Test
    @DisplayName("Searching for all comments of a nickname")
    void findByNickname() {
        // 1. Prepare input data
        String nickname = "Park";
        // 2. Actual data
        List<Comment> actual = commentRepository.findByNickname(nickname);
        // 3. Expected data
        Comment a = new Comment(1L, new Article(4L, "What is your favorite movie?", "Comment Go"), "Park", "Good Will Hunting");
        Comment b = new Comment(4L, new Article(5L, "What is your soul food?", "Comment Go Go"), "Park", "Chicken");
        Comment c = new Comment(7L, new Article(6L, "What is your hobby?", "Comment Go Go Go"), "Park", "Jogging");
        List<Comment> expected = Arrays.asList(a,b,c);
        // 4. Compare and Verify
        assertEquals(actual.toString(), expected.toString());
    }


    @Test
    @DisplayName("Searching for all comments of article #9")
    void testFindByArticleId() {
        // 1. Set an id
        Long id = 9L;
        // 2. Find comments via repository using id, that will become an actual object
        List<Comment> comments = commentRepository.findByArticleId(id);
        // 3. Set Article
        Article article = null;
        // 4. Set an expect object without an article parameter since id=9L doesn't exist.
        List<Comment> expected = Arrays.asList();
        // 5. Compare and verify
        assertEquals(comments.toString(), expected.toString());

    }
    @Test
    @DisplayName("Searching for all comments of article #999")
    void testFindByArticleId2() {
        Long id = 999L;
        List<Comment> comments = commentRepository.findByArticleId(id);
        Article article = null;
        List<Comment> expected = Arrays.asList();
        assertEquals(comments.toString(), expected.toString());
    }
    @Test
    @DisplayName("Searching for all comments of article #-1")
    void testFindByArticleId3() {
        Long id = -1L;
        List<Comment> comments = commentRepository.findByArticleId(id);
        Article article = null;
        List<Comment> expected = Arrays.asList();
        assertEquals(comments.toString(), expected.toString());
    }


    @Test
    @DisplayName("Searching for all comments of nickname 'Kim'")
    void testFindByNickname() {
        String nickname = "Kim";
        List<Comment> comments = commentRepository.findByNickname(nickname);
        Comment a = new Comment(2L, new Article(4L, "What is your favorite movie?", "Comment Go"),"Kim", "I am Sam");
        Comment b = new Comment(5L, new Article(5L, "What is your soul food?", "Comment Go Go"),"Kim", "Noodles");
        Comment c = new Comment(8L, new Article(6L, "What is your hobby?", "Comment Go Go Go"),"Kim", "Youtube");
        List<Comment> expected = Arrays.asList(a,b,c);
        assertEquals(comments.toString(), expected.toString());
    }
    @Test
    @DisplayName("Searching for all comments of nickname= 'null'")
    void testFindByNickname2() {
        String nickname = null;
        List<Comment> comments = commentRepository.findByNickname(nickname);
        List<Comment> expected = Arrays.asList();
        assertEquals(comments.toString(), expected.toString());
    }
    @Test
    @DisplayName("Searching for all comments of nickname=''")
    void testFindByNickname3() {
        String nickname = "";
        List<Comment> comments = commentRepository.findByNickname(nickname);
        List<Comment> expected = Arrays.asList();
        assertEquals(comments.toString(), expected.toString());
    }
}