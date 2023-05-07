package ru.kpfu.itis.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ArticleBriefResponse {
    private Integer id;
    private String title;
    private String slug;
    private Date createdAt;
}
