package ru.kpfu.itis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.model.Article;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@Component
public class ArticleRepository {
    @PersistenceContext
    private EntityManager em;

    public Article findBySlug(String slug) {
        TypedQuery<Article> query = em.createQuery("SELECT a FROM Article a WHERE a.slug = :slug", Article.class);
        query.setParameter("slug", slug);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public boolean save(Article article) {
        if (this.findBySlug(article.getSlug()) != null) {
            return false;
        }
        em.persist(article);
        return true;
    }

    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }

    @Transactional
    public void merge(Article article) {
        em.merge(article);
    }
}
