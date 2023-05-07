package ru.kpfu.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.kpfu.itis.exception.TitleParseException;
import ru.kpfu.itis.model.Article;
import ru.kpfu.itis.service.ArticleService;

import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String create() {
        return "article/edit";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String createHandler(@RequestParam String content) {
        Article article = articleService.saveByContent(content);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#detailView").arg(0, article.getSlug()).build();
    }

    @GetMapping("/{slug}")
    public String detailView(@PathVariable String slug, ModelMap map) {
        Article article = articleService.findBySlug(slug);
        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No article with that name.");
        }
        map.put("article", article);
        return "article/detailView";
    }

    @GetMapping("/")
    public String list(ModelMap map) {
        List<Article> articles = this.articleService.findAll();
        map.put("articles", articles);
        map.put("currentDate", new Date());
        return "article/list";
    }

    @RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(@PathVariable String slug, ModelMap map) {
        Article article = articleService.findBySlug(slug);
        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No article with that name.");
        }
        map.put("article", article);
        return "article/edit";
    }

    @RequestMapping(value = "/{slug}/edit", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public String editHandler(@PathVariable String slug, @RequestParam String content, ModelMap map) {
        Article article = articleService.findBySlug(slug);
        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No article with that name.");
        }
        articleService.updateContent(article, content);
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("AC#detailView").arg(0, article.getSlug()).build();
    }

}
