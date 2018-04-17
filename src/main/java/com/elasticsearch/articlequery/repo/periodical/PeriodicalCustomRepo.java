package com.elasticsearch.articlequery.repo.periodical;

import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PeriodicalCustomRepo {
    /**
     * 通过查询title和introduction进行全文搜索
     *
     * @param queryStr
     * @return
     */
    public DataVO SearchByQueryStr(String queryStr, Pageable pageable);

    /**
     * 通过多个条件进行精准搜索
     * @param title
     * @param author
     * @param publisher
     * @param type
     * @param introduction
     * @param pageable
     * @return
     */
    public DataVO SearchByParams(String title, String author, String publisher,
                                 String type, String introduction, Pageable pageable);

    /**
     * 根据提供方分类
     * @param publish
     * @return
     */
    public List<CommonVo> publishAgg(String publish);

    /**
     * 根据类别分类
     *
     * @return
     */
    public List<CommonVo> typeAgg(String type);

    /**
     * 根据发表时间做日期直方图聚合
     *
     * @return
     */
    public List<CommonVo> dateHistogramAgg(String interval);
}

