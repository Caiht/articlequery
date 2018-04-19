package com.elasticsearch.articlequery.controller;


import com.elasticsearch.articlequery.models.Patent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatentController {

    private final PatentRepo patentRepo;

    @GetMapping(value = "/api/patent/search/all")
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
        return patentRepo.SearchByQueryStr(queryStr, gtDate, ltDate, pageable);

    }


    @GetMapping(value = "/api/patent/search/params")
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

    @GetMapping(value = "/api/patent/delete")
    public void delete(@RequestParam(value = "id", required = true) Long id) {
        patentRepo.deleteById(id);
    }

    @GetMapping(value = "/api/patent/save")
    public void save(@RequestParam(value = "id", required = false) Long id,
                     @RequestParam(value = "title", required = false) String title,
                     @RequestParam(value = "requestNumber", required = false) String requestNumber,
                     @RequestParam(value = "requestDate", required = false) String requestDate,
                     @RequestParam(value = "publicationNumber", required = false) String publicationNumber,
                     @RequestParam(value = "publicationDate", required = false) String publicationDate,
                     @RequestParam(value = "proposer", required = false) String proposer,
                     @RequestParam(value = "inventor", required = false) String inventor,
                     @RequestParam(value = "introduction", required = false) String introduction,
                     @RequestParam(value = "type", required = false) String type) throws ParseException {
        Patent patent = new Patent();
        if (id == null)
            patent.setId(patentRepo.count() + 1);
        else
            patent.setId(id);
        patent.setTitle(title);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Pattern pattern =Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
        patent.setRequestNumber(requestNumber);
        if (requestDate!=null&&pattern.matcher(requestDate).matches()){
            patent.setRequestDate(sdf.parse(requestDate));
        }
        patent.setPublicationNumber(publicationNumber);
        if (publicationDate!=null&&pattern.matcher(publicationDate).matches()){
            patent.setPublicationDate(sdf.parse(publicationDate));
        }
        patent.setProposer(proposer);
        patent.setInventor(inventor);
        patent.setType(type);
        patent.setIntroduction(introduction);

        patentRepo.save(patent);
    }

}
