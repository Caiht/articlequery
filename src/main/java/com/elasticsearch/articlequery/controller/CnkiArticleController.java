package com.elasticsearch.articlequery.controller;

import com.elasticsearch.articlequery.models.CnkiArticle;
import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import com.elasticsearch.articlequery.repo.cnkiArticle.CnkiArticleRepo;
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
public class CnkiArticleController {

    private final CnkiArticleRepo cnkiArticleRepo;


    @GetMapping(value = "/api/article/cnki/search/all")
    public DataVO SearchByQueryStr(@RequestParam(value = "queryStr", required = false) String queryStr,
                                   @RequestParam(value = "gtDate", required = false) String gtDate,
                                   @RequestParam(value = "ltDate", required = false) String ltDate,
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
        return cnkiArticleRepo.SearchByQueryStr(queryStr, gtDate, ltDate, pageable);
    }


    @GetMapping(value = "/api/article/cnki/search/params")
    public DataVO SearchByParams(@RequestParam(value = "title", required = false) String title,
                                 @RequestParam(value = "author", required = false) String author,
                                 @RequestParam(value = "teacher", required = false) String teacher,
                                 @RequestParam(value = "university", required = false) String university,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "introduction", required = false) String introduction,
                                 @RequestParam(value = "gtDate", required = false) String gtDate,
                                 @RequestParam(value = "ltDate", required = false) String ltDate,
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
        return cnkiArticleRepo.SearchByParams(title, author, teacher, university, type, introduction, gtDate, ltDate, pageable);
    }


    @GetMapping(value = "/api/article/cnki/agg/university")
    public List<CommonVo> universityAgg(@RequestParam(value = "university", required = false) String university) {
        return cnkiArticleRepo.universityAgg(university);
    }


    @GetMapping(value = "/api/article/cnki/agg/type")
    public List<CommonVo> typeAgg(@RequestParam(value = "type", required = false) String type) {
        return cnkiArticleRepo.typeAgg(type);

    }


    @GetMapping(value = "/api/article/cnki/agg/dateHistogram")
    public List<CommonVo> dateHistogramAgg(String interval) {
        return cnkiArticleRepo.dateHistogramAgg(interval);

    }

    @GetMapping(value = "/api/article/cnki/delete")
    public void delete(@RequestParam(value = "id", required = true) Long id) {
        cnkiArticleRepo.deleteById(id);
    }

    @GetMapping(value = "/api/article/cnki/save")
    public void save(@RequestParam(value = "id", required = false) Long id,
                     @RequestParam(value = "title", required = false) String title,
                     @RequestParam(value = "author", required = false) String author,
                     @RequestParam(value = "teacher", required = false) String teacher,
                     @RequestParam(value = "university", required = false) String university,
                     @RequestParam(value = "type", required = false) String type,
                     @RequestParam(value = "introduction", required = false) String introduction) {
        CnkiArticle cnkiArticle = new CnkiArticle();
        if (id == null)
            cnkiArticle.setId(cnkiArticleRepo.count() + 1);
        else
            cnkiArticle.setId(id);
        cnkiArticle.setTitle(title);
        cnkiArticle.setAuthor(author);
        cnkiArticle.setTeacher(teacher);
        cnkiArticle.setUniversity(university);
        cnkiArticle.setType(type);
        cnkiArticle.setIntroduction(introduction);

        cnkiArticleRepo.save(cnkiArticle);
    }
}
