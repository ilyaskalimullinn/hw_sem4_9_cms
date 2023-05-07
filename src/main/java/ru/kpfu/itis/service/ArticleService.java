package ru.kpfu.itis.service;

import com.github.slugify.Slugify;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.exception.SuspiciousRequestException;
import ru.kpfu.itis.exception.TitleParseException;
import ru.kpfu.itis.model.Article;
import ru.kpfu.itis.repository.ArticleRepository;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private AntiSamy antiSamy;

    @Autowired
    private Slugify slugify;

    @Autowired
    private ArticleRepository articleRepository;

    public Article saveByContent(String content) {
        Article article = new Article();
        this.updateArticleByContent(article, content);

        while (!this.articleRepository.save(article)) {
            article.setSlug(this.modifySlug(article.getSlug()));
        }

        return article;
    }

    protected String getTitle(String content) {
        Document document = Jsoup.parse(content);

        Element titleElement = document.selectFirst("h1");
        if (titleElement != null) {
            return titleElement.text();
        }

        for (Node node : document.body().childNodes()) {
            if (node instanceof Element) {
                Element e = (Element) node;
                if (e.text().equals("")) continue;
                return e.text();
            }
        }

        throw new TitleParseException("No suitable node to choose title.");
    }

    protected String modifySlug(String slug) {
        return slug + "-new";
    }

    public Article findBySlug(String slug) {
        return this.articleRepository.findBySlug(slug);
    }

    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }

    public void updateContent(Article article, String content) {
        this.updateArticleByContent(article, content);
        this.articleRepository.merge(article);

    }

    protected void updateArticleByContent(Article article, String content) {
        try {
            String clearContent = antiSamy.scan(content).getCleanHTML();
            String title = this.getTitle(clearContent);
            String slug = this.slugify.slugify(title);

            article.setContent(clearContent);
            article.setTitle(title);
            article.setSlug(slug);
        } catch (ScanException | PolicyException e) {
            throw new SuspiciousRequestException();
        }
    }
}
