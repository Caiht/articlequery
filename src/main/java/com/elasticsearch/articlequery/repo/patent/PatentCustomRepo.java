package com.elasticsearch.articlequery.repo.patent;


import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatentCustomRepo {
    /**
     * 通过查询title和introduction进行全文搜索
     * 并根据时间范围过滤
     *
     * @param queryStr
     * @param ltDate
     * @param gtDate
     * @return
     */
    public DataVO SearchByQueryStr(String queryStr, String gtDate, String ltDate, Pageable pageable);

    /**
     * 通过多个条件进行精准搜索
     *
     * @param title
     * @param requestNumber
     * @param publicationNumber
     * @param proposer
     * @param inventor
     * @param introduction
     * @param type
     * @return
     */
    public DataVO SearchByParams(String title, String requestNumber, String publicationNumber,
                                                   String proposer, String inventor, String introduction,
                                                   String type, Pageable pageable);

    /**
     * 根据种类分类
     * @param type
     * @return
     */
    public List<CommonVo> typeAgg(String type);

    /**根据发表时间做日期直方图聚合
     * @return
     */
    public List<CommonVo> dateHistogramAgg(String dateType ,String interval);
}
