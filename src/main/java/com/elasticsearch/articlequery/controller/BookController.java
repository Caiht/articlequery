package com.elasticsearch.articlequery.controller;


import com.elasticsearch.articlequery.models.Book;
import com.elasticsearch.articlequery.models.vos.BookVO;
import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import com.elasticsearch.articlequery.repo.book.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {
    private final BookRepo bookRepo;


    @GetMapping(value = "/api/book/search/all")
    public DataVO SearchByQueryStr(@RequestParam(value = "queryStr", required = false) String queryStr,
                                   @RequestParam(value = "pageNum", required = true, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", required = true, defaultValue = "20") int pageSize) {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return pageNum - 1;
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


    @GetMapping(value = "/api/book/search/params")
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
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return pageNum - 1;
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
    public List<BookVO> tagAgg(@RequestParam(value = "tag", required = false) String tag) {
        return bookRepo.tagAgg(tag);
    }


    @GetMapping(value = "/api/book/agg/dateHistogram")
    public List<CommonVo> dateHistogramAgg(String interval) {
        return bookRepo.dateHistogramAgg(interval);
    }

    @GetMapping(value = "/api/book/delete")
    public void delete(@RequestParam(value = "id", required = true) Long id) {
        bookRepo.deleteById(id);
    }

    @GetMapping(value = "/api/book/save")
    public void save(@RequestParam(value = "id", required = false) Long id,
                     @RequestParam(value = "title", required = false) String title,
                     @RequestParam(value = "author", required = false) String author,
                     @RequestParam(value = "publish", required = false) String publish,
                     @RequestParam(value = "score", required = false) String score,
                     @RequestParam(value = "person", required = false) String person,
                     @RequestParam(value = "tag", required = false) String tag,
                     @RequestParam(value = "introduction", required = false) String introduction,
                     @RequestParam(value = "price", required = false) String price,
                     @RequestParam(value = "date", required = false) String date,
                     @RequestParam(value = "isbn", required = false) String isbn) {
        Book book = new Book();
        if (id == null)
            book.setId(bookRepo.count() + 1);
        else
            book.setId(id);
        if (title != null && !"undefined".equals(title))
            book.setTitle(title);
        if (author != null && !"undefined".equals(author))
            book.setAuthor(author);
        if (publish != null && !"undefined".equals(publish))
            book.setPublish(publish);
        if (score != null && !"undefined".equals(score))
            book.setScore(Double.parseDouble(score));
        if (tag != null && !"undefined".equals(tag))
            book.setTag(tag);
        if (introduction != null && !"undefined".equals(introduction))
            book.setIntroduction(introduction);
        if (price != null && !"undefined".equals(price))
            book.setPrice(Double.parseDouble(price));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Pattern pattern =Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
        if (date != null && !"undefined".equals(date)) {
            try {
                Date newDate = sdf.parse(date);
                book.setDate(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (isbn != null && !"undefined".equals(isbn))
            book.setIsbn(isbn);
        if (person != null && !"undefined".equals(person))
            book.setPerson(person);
        bookRepo.save(book);
    }

    @PostMapping(value = "/api/book/save/title")
    public Boolean savetitle(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author) {
        Book book = new Book();
        book.setId(bookRepo.count() + 1);
        if (title != null && !"undefined".equals(title))
            book.setTitle(title);
        if (author != null && !"undefined".equals(author))
            book.setAuthor(author);
        bookRepo.save(book);
        return true;
    }

    @PostMapping(value = "/api/book/save/title2")
    public Boolean savebbb(
            @RequestParam(value = "book", required = false) Book book) {
        bookRepo.save(book);
        return true;
    }

    @PostMapping(value = "/api/book/test")
    public Boolean test(@RequestParam(value = "corpId", required = false) String corpId,
                        @RequestParam(value = "agentId", required = false) String agentId,
                        @RequestParam(value = "payload", required = false) String payload,
                        @RequestParam(value = "users", required = false) String[] users) {
        if (users != null && users.length > 0) {
            System.out.println(users[0]);
        }
        System.out.println(corpId + agentId + payload);
        return true;
    }
}
