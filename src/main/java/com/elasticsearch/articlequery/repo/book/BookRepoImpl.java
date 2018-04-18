package com.elasticsearch.articlequery.repo.book;


import com.elasticsearch.articlequery.models.Book;
import com.elasticsearch.articlequery.models.vos.BookVO;
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
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookRepoImpl implements BookCustomRepo{

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public DataVO SearchByQueryStr(String queryStr, Pageable pageable) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(queryStr)&&!"undefined".equals(queryStr)) {
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", queryStr));
            boolQueryBuilder.should(QueryBuilders.matchQuery("introduction", queryStr));
            boolQueryBuilder.minimumShouldMatch(1);
        }
        searchQuery.withIndices("book").withTypes("book").withQuery(boolQueryBuilder).withPageable(pageable);
        val page = elasticsearchTemplate.queryForPage(searchQuery.build(), Book.class);
        return new DataVO(page.getTotalElements(), page.getContent());
    }

    @Override
    public DataVO SearchByParams(String title, String author, String publish, String gtScore, String ltScore, String tag, String introduction, String gtPrice, String ltPrice,String isbn, Pageable pageable) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(title)&&!"undefined".equals(title)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("title", title));
        }
        if (StringUtils.isNotBlank(author)&&!"undefined".equals(author)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("author", author));
        }
        if (StringUtils.isNotBlank(publish)&&!"undefined".equals(publish)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("publish", publish));
        }
        if (StringUtils.isNotBlank(gtScore)&&!"undefined".equals(gtScore)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("score").gte(Double.parseDouble(gtScore)));
        }
        if (StringUtils.isNotBlank(ltScore)&&!"undefined".equals(ltScore)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("score").lte(Double.parseDouble(ltScore)));
        }
        if (StringUtils.isNotBlank(tag)&&!"undefined".equals(tag)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("tag", tag));
        }
        if (StringUtils.isNotBlank(introduction)&&!"undefined".equals(introduction)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("introduction", introduction));
        }
        if (StringUtils.isNotBlank(gtPrice)&&!"undefined".equals(gtPrice)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gte(Double.parseDouble(gtPrice)));
        }
        if (StringUtils.isNotBlank(ltPrice)&&!"undefined".equals(ltPrice)) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Double.parseDouble(ltPrice)));
        }
        if (StringUtils.isNotBlank(isbn)&&!"undefined".equals(isbn)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("isbn.keyword", isbn));
        }
        searchQuery.withIndices("book").withTypes("book").withQuery(boolQueryBuilder).withPageable(pageable);
        val page = elasticsearchTemplate.queryForPage(searchQuery.build(), Book.class);
        return new DataVO(page.getTotalElements(), page.getContent());
    }

    @Override
    public List<BookVO> publishAgg(String publish) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(publish)&&!"undefined".equals(publish))
            boolQueryBuilder.must(QueryBuilders.termQuery("publish.keyword", publish));
        val agg = AggregationBuilders.terms("term_agg").field("publish.keyword")
                .subAggregation(AggregationBuilders.sum("priceSum").field("price"))
                .subAggregation(AggregationBuilders.avg("scoreAvg").field("score"))
                .size(10000);
        searchQuery.addAggregation(agg);
        searchQuery.withIndices("book").withTypes("book").withQuery(boolQueryBuilder);
        val aggregations = elasticsearchTemplate.query(searchQuery.build(), SearchResponse::getAggregations);
        Terms agg1 = aggregations.get("term_agg");
        return agg1.getBuckets().stream().map(Terms.Bucket.class::cast).map(bucket -> {
            val priceSum = Sum.class.cast(bucket.getAggregations().get("priceSum")).getValueAsString();
            val scoreAvg = Avg.class.cast(bucket.getAggregations().get("scoreAvg")).getValueAsString();
            return new BookVO(bucket.getKeyAsString(), new BigDecimal(priceSum), bucket.getDocCount(), new BigDecimal(scoreAvg));
        }).collect(Collectors.toList());
    }

    @Override
    public List<BookVO> tagAgg(String tag) {
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(tag)&&!"undefined".equals(tag))
            boolQueryBuilder.must(QueryBuilders.termQuery("tag.keyword", tag));
        val agg = AggregationBuilders.terms("term_agg").field("tag.keyword")
                .subAggregation(AggregationBuilders.sum("priceSum").field("price"))
                .subAggregation(AggregationBuilders.avg("scoreAvg").field("score"))
                .size(10000);
        searchQuery.addAggregation(agg);
        searchQuery.withIndices("book").withTypes("book").withQuery(boolQueryBuilder);
        val aggregations = elasticsearchTemplate.query(searchQuery.build(), SearchResponse::getAggregations);
        Terms agg1 = aggregations.get("term_agg");
        return agg1.getBuckets().stream().map(Terms.Bucket.class::cast).map(bucket -> {
            val priceSum = Sum.class.cast(bucket.getAggregations().get("priceSum")).getValueAsString();
            val scoreAvg = Avg.class.cast(bucket.getAggregations().get("scoreAvg")).getValueAsString();
            return new BookVO(bucket.getKeyAsString(), new BigDecimal(priceSum), bucket.getDocCount(), new BigDecimal(scoreAvg));
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
        searchQuery.withIndices("book").withTypes("book").withQuery(boolQueryBuilder);
        val aggregations = elasticsearchTemplate.query(searchQuery.build(), SearchResponse::getAggregations);
        Histogram agg1 = aggregations.get("date_agg");
        if (StringUtils.isNotBlank(interval)&&"year".equals(interval))
            return agg1.getBuckets().stream().map(Histogram.Bucket.class::cast)
                    .map(bucket->new CommonVo(DateTime.class.cast(bucket.getKey()).toString("yyyy"), bucket.getDocCount()))
                    .collect(Collectors.toList());
        else
            return agg1.getBuckets().stream().map(Histogram.Bucket.class::cast)
                    .map(bucket -> new CommonVo(DateTime.class.cast(bucket.getKey()).toString("yyyy-MM"), bucket.getDocCount()))
                    .collect(Collectors.toList());
    }
}
