package com.elasticsearch.articlequery.controller;


import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import com.elasticsearch.articlequery.repo.periodical.PeriodicalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PeriodicalController {

    private final PeriodicalRepo periodicalRepo;


    @GetMapping(value = "/api/periodical/search/all")
    public DataVO SearchByQueryStr(@RequestParam(value = "queryStr", required = false) String queryStr,
                                   @RequestParam(value = "pageNum",  defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize",  defaultValue = "20") int pageSize) {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return pageNum-1;
            }

            @Override
            public int getPageSize() {
                return pageSize;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        return periodicalRepo.SearchByQueryStr(queryStr, pageable);

    }


    @GetMapping(value = "/api/periodical/search/params")
    public DataVO SearchByParams(@RequestParam(value = "title", required = false) String title,
                                 @RequestParam(value = "author", required = false) String author,
                                 @RequestParam(value = "publish", required = false) String publish,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "introduction", required = false) String introduction,
                                 @RequestParam(value = "pageNum",  defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize",  defaultValue = "20") int pageSize) {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return pageNum-1;
            }

            @Override
            public int getPageSize() {
                return pageSize;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        return periodicalRepo.SearchByParams(title, author, publish, type, introduction, pageable);
    }


    @GetMapping(value = "/api/periodical/agg/publish")
    public List<CommonVo> publishAgg(@RequestParam(value = "publish", required = false) String publish) {
        return periodicalRepo.publishAgg(publish);

    }


    @GetMapping(value = "/api/periodical/agg/type")
    public List<CommonVo> typeAgg(@RequestParam(value = "type", required = false) String type) {
        return periodicalRepo.typeAgg(type);
    }


    @GetMapping(value = "/api/periodical/agg/dateHistogram")
    public List<CommonVo> dateHistogramAgg(String interval) {
        return periodicalRepo.dateHistogramAgg(interval);
    }
}
