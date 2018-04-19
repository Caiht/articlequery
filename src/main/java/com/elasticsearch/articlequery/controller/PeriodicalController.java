package com.elasticsearch.articlequery.controller;


import com.elasticsearch.articlequery.models.Periodical;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PeriodicalController {

    private final PeriodicalRepo periodicalRepo;


    @GetMapping(value = "/api/periodical/search/all")
    public DataVO SearchByQueryStr(@RequestParam(value = "queryStr", required = false) String queryStr,
                                   @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
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
        return periodicalRepo.SearchByQueryStr(queryStr, pageable);

    }


    @GetMapping(value = "/api/periodical/search/params")
    public DataVO SearchByParams(@RequestParam(value = "title", required = false) String title,
                                 @RequestParam(value = "author", required = false) String author,
                                 @RequestParam(value = "publish", required = false) String publish,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "introduction", required = false) String introduction,
                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
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

    @GetMapping(value = "/api/periodical/delete")
    public void delete(@RequestParam(value = "id", required = true) Long id) {
        periodicalRepo.deleteById(id);
    }

    @GetMapping(value = "/api/periodical/save")
    public void save(@RequestParam(value = "id", required = false) Long id,
                     @RequestParam(value = "title", required = false) String title,
                     @RequestParam(value = "author", required = false) String author,
                     @RequestParam(value = "publish", required = false) String publish,
                     @RequestParam(value = "type", required = false) String type,
                     @RequestParam(value = "date", required = false) String date,
                     @RequestParam(value = "introduction", required = false) String introduction) throws ParseException {
        Periodical periodical = new Periodical();
        if (id == null)
            periodical.setId(periodicalRepo.count() + 1);
        else
            periodical.setId(id);
        periodical.setTitle(title);
        periodical.setAuthor(author);
        periodical.setPublisher(publish);
        periodical.setType(type);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Pattern pattern =Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
        periodical.setIntroduction(introduction);
        if (date!=null&&pattern.matcher(date).matches()){
            periodical.setDate(sdf.parse(date));
        }
        periodicalRepo.save(periodical);
    }
}
