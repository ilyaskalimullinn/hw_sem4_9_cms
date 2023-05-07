package ru.kpfu.itis.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ArticleCreateResponse {
    private String status;
    private ArticleBriefResponse article;
    private Date createdAt;
}
