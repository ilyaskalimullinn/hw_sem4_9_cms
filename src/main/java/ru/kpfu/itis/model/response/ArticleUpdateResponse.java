package ru.kpfu.itis.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleUpdateResponse {
    private String status;
    private ArticleBriefResponse article;
}
