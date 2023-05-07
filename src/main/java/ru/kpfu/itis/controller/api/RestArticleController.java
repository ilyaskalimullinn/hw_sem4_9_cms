package ru.kpfu.itis.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.kpfu.itis.exception.TitleParseException;
import ru.kpfu.itis.model.Article;
import ru.kpfu.itis.model.request.ArticleCreateRequest;
import ru.kpfu.itis.model.request.ArticleUpdateRequest;
import ru.kpfu.itis.model.response.ArticleBriefResponse;
import ru.kpfu.itis.model.response.ArticleCreateResponse;
import ru.kpfu.itis.model.response.ArticleDetailResponse;
import ru.kpfu.itis.model.response.ArticleUpdateResponse;
import ru.kpfu.itis.service.ArticleService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/article")
@Tag(name = "Book Simple CRUD")
public class RestArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("")
    @Operation(summary = "Get list of all articles")
    public List<ArticleBriefResponse> list() {
        return this.articleService.findAll()
                .stream()
                .map(article -> ArticleBriefResponse.builder()
                        .title(article.getTitle())
                        .slug(article.getSlug())
                        .id(article.getId())
                        .createdAt(article.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Get article by slug")
    public ArticleDetailResponse detailView(@PathVariable String slug) {
        Article article = articleService.findBySlug(slug);
        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No article with that name.");
        }
        return ArticleDetailResponse.builder()
                .content(article.getContent())
                .slug(article.getSlug())
                .title(article.getTitle())
                .createdAt(article.getCreatedAt())
                .build();
    }

    @PostMapping("")
    @Operation(summary = "Create article")
    public ArticleCreateResponse create(@RequestBody @Valid ArticleCreateRequest articleCreateRequest,
                                        BindingResult result) {
        try {
            Article article = articleService.saveByContent(articleCreateRequest.getContent());
            ArticleBriefResponse brief = ArticleBriefResponse.builder()
                    .title(article.getTitle())
                    .id(article.getId())
                    .slug(article.getSlug())
                    .createdAt(article.getCreatedAt())
                    .build();

            return ArticleCreateResponse.builder()
                    .article(brief)
                    .status("success")
                    .build();
        } catch (TitleParseException e) {
            return ArticleCreateResponse.builder()
                    .article(null)
                    .status("Could not parse title")
                    .build();
        }

    }

    @PutMapping("/{slug}")
    @Operation(summary = "Edit article")
    public ArticleUpdateResponse update(@PathVariable String slug,
                                        @RequestBody @Valid ArticleUpdateRequest articleUpdateRequest,
                                        BindingResult result) {
        Article article = articleService.findBySlug(slug);
        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No article with that name.");
        }
        try {
            articleService.updateContent(article, articleUpdateRequest.getContent());
            ArticleBriefResponse brief = ArticleBriefResponse.builder()
                    .title(article.getTitle())
                    .id(article.getId())
                    .slug(article.getSlug())
                    .build();

            return ArticleUpdateResponse.builder()
                    .article(brief)
                    .status("success")
                    .build();

        } catch (TitleParseException e) {
            return ArticleUpdateResponse.builder()
                    .status("Could not parse title")
                    .article(null)
                    .build();
        }
    }
}
