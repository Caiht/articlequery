package com.elasticsearch.articlequery.controller;


import com.elasticsearch.articlequery.models.vos.BookVO;
import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import com.elasticsearch.articlequery.repo.book.BookRepo;
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
public class BookController {
    private final BookRepo bookRepo;


    @GetMapping(value = "/api/book/SearchByQueryStr")
    public DataVO SearchByQueryStr(@RequestParam(value = "queryStr", required = false) String queryStr,
                                   @RequestParam(value="pageNum", required=true, defaultValue="1")int pageNum,
                                   @RequestParam(value="pageSize", required=true, defaultValue="20")int pageSize) {
        Pageable pageable =new Pageable() {
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
        return bookRepo.SearchByQueryStr(queryStr, pageable);
    }


    @GetMapping(value = "/api/book/SearchByParams")
    public DataVO SearchByParams(@RequestParam(value = "title", required = false) String title,
                                 @RequestParam(value = "author", required = false) String author,
                                 @RequestParam(value = "publish", required = false) String publish,
                                 @RequestParam(value = "gtScore", required = false) String gtScore,
                                 @RequestParam(value = "ltScore", required = false) String ltScore,
                                 @RequestParam(value = "tag", required = false) String tag,
                                 @RequestParam(value = "introduction", required = false) String introduction,
                                 @RequestParam(value = "gtPrice", required = false) String gtPrice,
                                 @RequestParam(value = "ltPrice", required = false) String ltPrice,
                                 @RequestParam(value = "isbn", required = false) String isbn,
                                 @RequestParam(value="pageNum", defaultValue="1")int pageNum,
                                 @RequestParam(value="pageSize", defaultValue="20")int pageSize
                                 ) {
        Pageable pageable =new Pageable() {
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
        return bookRepo.SearchByParams(title, author, publish, gtScore, ltScore, tag, introduction, gtPrice, ltPrice, isbn, pageable);

    }


    @GetMapping(value = "/api/book/agg/publish")
    public List<BookVO> publishAgg(@RequestParam(value = "publish", required = false) String publish) {
        return bookRepo.publishAgg(publish);
    }



    @GetMapping(value = "/api/book/agg/tag")
    public List<BookVO> tagAgg( @RequestParam(value = "tag", required = false) String tag) {
        return bookRepo.tagAgg(tag);
    }


    @GetMapping(value = "/api/book/agg/dateHistogram")
    public List<CommonVo> dateHistogramAgg(String interval) {
        return bookRepo.dateHistogramAgg(interval);
    }

}
