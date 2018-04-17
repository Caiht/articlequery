package com.elasticsearch.articlequery.repo.cnkiArticle;


import com.elasticsearch.articlequery.models.CnkiArticle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnkiArticleRepo extends CnkiArticleCustomRepo, ElasticsearchRepository<CnkiArticle, Long> {
}
