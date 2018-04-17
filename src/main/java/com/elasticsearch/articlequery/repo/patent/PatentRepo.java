package com.elasticsearch.articlequery.repo.patent;

import com.elasticsearch.articlequery.models.Patent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatentRepo extends PatentCustomRepo, ElasticsearchRepository<Patent, Long> {
}
