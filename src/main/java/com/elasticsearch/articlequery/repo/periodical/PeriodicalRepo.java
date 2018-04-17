package com.elasticsearch.articlequery.repo.periodical;

import com.elasticsearch.articlequery.models.Periodical;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodicalRepo extends PeriodicalCustomRepo, ElasticsearchRepository<Periodical, Long> {

}
