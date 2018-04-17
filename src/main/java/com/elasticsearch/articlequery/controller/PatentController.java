package com.elasticsearch.articlequery.controller;


import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import com.elasticsearch.articlequery.repo.patent.PatentRepo;
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
public class PatentController {

    private final PatentRepo patentRepo;

    @GetMapping(value = "/api/patent/SearchByQueryStr")
    public DataVO SearchByQueryStr(@RequestParam(value = "queryStr", required = false) String queryStr,
                                   @RequestParam(value = "gtDate", required = false) String gtDate,
                                   @RequestParam(value = "ltDate", required = false) String ltDate,
                                   @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", required = true, defaultValue = "20") int pageSize) {
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
        return patentRepo.SearchByQueryStr(queryStr, gtDate, ltDate, pageable);

    }


    @GetMapping(value = "/api/patent/SearchByParams")
    public DataVO SearchByParams(@RequestParam(value = "title", required = false) String title,
                                 @RequestParam(value = "requestNumber", required = false) String requestNumber,
                                 @RequestParam(value = "publicationNumber", required = false) String publicationNumber,
                                 @RequestParam(value = "proposer", required = false) String proposer,
                                 @RequestParam(value = "inventor", required = false) String inventor,
                                 @RequestParam(value = "introduction", required = false) String introduction,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", required = true, defaultValue = "20") int pageSize) {
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
        return patentRepo.SearchByParams(title, requestNumber, publicationNumber, proposer, inventor, introduction, type, pageable);

    }


    @GetMapping(value = "/api/patent/agg/type")
    public List<CommonVo> typeAgg(@RequestParam(value = "type", required = false) String type) {
        return patentRepo.typeAgg(type);

    }


    @GetMapping(value = "/api/patent/agg/dateHistogram")
    public List<CommonVo> dateHistogramAgg(String dateType, String interval) {
        return patentRepo.dateHistogramAgg(dateType, interval);
    }


}
