package com.example.articleboard.articles;

import com.example.articleboard.dto.ArticleForm;
import com.example.articleboard.dto.CommentDto;
import com.example.articleboard.entity.Article;
import com.example.articleboard.repository.ArticleRepository;
import com.example.articleboard.repository.CommentRepository;
import com.example.articleboard.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository; // repository dependency injection

    @Autowired
    CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "/articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){

        log.info(form.toString());
        // 1. DTO to Entity
        Article article = form.toEntity();
        log.info(article.toString());
        // 2. Save Entity to DB via repository
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        Article articleEntity = articleRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("no Id found from DB"));
        List<CommentDto> commentDtos = commentService.comments(id);
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        List<Article> articleEntityList = articleRepository.findAll();
        model.addAttribute("articleList", articleEntityList);
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 1. Extract the data with id from the articleRepository
        Article articleEntity = articleRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("id doesn't exit at DB"));
        // 2. Add the data into model
        model.addAttribute("article",articleEntity);
        // 3. Return the redirect:/url of the data with the id.
        // in this case, the url is show controller.
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        // 1. dto to entity
        Article articleEntity = form.toEntity();
        // 2. validation check for the id at DB
        Article target = articleRepository.findById(articleEntity.getId())
                .orElseThrow(()-> new IllegalArgumentException("id doesn't exist at DB"));
        // 3. add entity into DB via repository
        articleRepository.save(articleEntity);

        return "redirect:/articles/"+articleEntity.getId();

    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redir){
        log.info("Deleted Request Received");
        // 1. Find the entity using id at the DB or else, throw one of RuntimeExceptions
        Article target = articleRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("id unmatched at DB"));
        log.info(target.toString());
        // 2. If the entity is legit, delete it via repository.delete()
        articleRepository.delete(target);
        // 3. Print the deleted item using RedirectAttributes
        redir.addFlashAttribute("msg", "Item Deleted!");
        // 4. Redirect url with displaying deleted item(#1)
        return "redirect:/articles";
    }
}
