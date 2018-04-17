package com.elasticsearch.articlequery.repo.cnkiArticle;


import com.elasticsearch.articlequery.models.CnkiArticle;
import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CnkiArticleRepoImpl implements CnkiArticleCustomRepo {

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public DataVO SearchByQueryStr(String queryStr, String gtDate, String ltDate, Pageable pageable) {

        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(gtDate)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("date").gte(gtDate).format("yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(ltDate)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("date").lte(ltDate).format("yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(queryStr)&&!"undefined".equals(queryStr)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", queryStr));
            boolQueryBuilder.should(QueryBuilders.matchQuery("introduction", queryStr));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        searchQuery.withIndices("cnki_article").withTypes("cnki_article").withQuery(boolQueryBuilder).withPageable(pageable);
        val page = elasticsearchTemplate.queryForPage(searchQuery.build(), CnkiArticle.class);
        return new DataVO(page.getTotalElements(), page.getContent());
    }

    @Override
    public DataVO SearchByParams(String title, String author, String teacher, String university, String type, String introduction, String gtDate, String ltDate, Pageable pageable) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(gtDate)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("date").gte(gtDate).format("yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(ltDate)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("date").lte(ltDate).format("yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(title)&&!"undefined".equals(title)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
        }
        if (StringUtils.isNotBlank(author)&&!"undefined".equals(author)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("author", author));
        }
        if (StringUtils.isNotBlank(teacher)&&!"undefined".equals(teacher)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("teacher", teacher));
        }
        if (StringUtils.isNotBlank(university)&&!"undefined".equals(university)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("university", university));
        }
        if (StringUtils.isNotBlank(type)&&!"undefined".equals(type)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("type", type));
        }
        if (StringUtils.isNotBlank(introduction)&&!"undefined".equals(introduction)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("introduction", introduction));
        }
        searchQuery.withIndices("cnki_article").withTypes("cnki_article").withQuery(boolQueryBuilder).withPageable(pageable);
        val page = elasticsearchTemplate.queryForPage(searchQuery.build(), CnkiArticle.class);
        return new DataVO(page.getTotalElements(), page.getContent());
    }

    @Override
    public List<CommonVo> universityAgg(String university) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(university)&&!"undefined".equals(university))
            boolQueryBuilder.must(QueryBuilders.termQuery("university.keyword", university));
        val agg = AggregationBuilders.terms("term_agg").field("university.keyword").size(10000);
        searchQuery.addAggregation(agg);
        searchQuery.withIndices("cnki_article").withTypes("cnki_article").withQuery(boolQueryBuilder);
        val aggregations = elasticsearchTemplate.query(searchQuery.build(), SearchResponse::getAggregations);
        Terms agg1 = aggregations.get("term_agg");
        return agg1.getBuckets().stream().map(Terms.Bucket.class::cast).map(bucket -> {
            return new CommonVo(bucket.getKeyAsString(),
                    bucket.getDocCount());
        }).collect(Collectors.toList());
    }

    @Override
    public List<CommonVo> typeAgg(String type) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(type)&&!"undefined".equals(type))
            boolQueryBuilder.must(QueryBuilders.termQuery("type.keyword", type));
        val agg = AggregationBuilders.terms("term_agg").field("type.keyword").size(10000);
        searchQuery.addAggregation(agg);
        searchQuery.withIndices("cnki_article").withTypes("cnki_article").withQuery(boolQueryBuilder);
        val aggregations = elasticsearchTemplate.query(searchQuery.build(), SearchResponse::getAggregations);
        Terms agg1 = aggregations.get("term_agg");
        return agg1.getBuckets().stream().map(Terms.Bucket.class::cast).map(bucket -> {
            return new CommonVo(bucket.getKeyAsString(),
                    bucket.getDocCount());
        }).collect(Collectors.toList());
    }

    @Override
    public List<CommonVo> dateHistogramAgg(String interval) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        val agg = AggregationBuilders.dateHistogram("date_agg").field("date").minDocCount(1);
        if (StringUtils.isNotBlank(interval)&&"year".equals(interval))
            agg.dateHistogramInterval(DateHistogramInterval.YEAR);
        else
            agg.dateHistogramInterval(DateHistogramInterval.MONTH);
        searchQuery.addAggregation(agg);
        searchQuery.withIndices("cnki_article").withTypes("cnki_article").withQuery(boolQueryBuilder);
        val aggregations = elasticsearchTemplate.query(searchQuery.build(), SearchResponse::getAggregations);
        Histogram agg1 = aggregations.get("date_agg");
        if (StringUtils.isNotBlank(interval)&&"year".equals(interval))
            return agg1.getBuckets().stream().map(Histogram.Bucket.class::cast).map(bucket -> {
                return new CommonVo(DateTime.class.cast(bucket.getKey()).toString("yyyy"),
                        bucket.getDocCount());
            }).collect(Collectors.toList());
        else
            return agg1.getBuckets().stream().map(Histogram.Bucket.class::cast).map(bucket -> {
                return new CommonVo(DateTime.class.cast(bucket.getKey()).toString("yyyy-MM"),
                        bucket.getDocCount());
            }).collect(Collectors.toList());
    }


}
