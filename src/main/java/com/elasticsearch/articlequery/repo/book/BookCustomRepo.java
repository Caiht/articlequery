package com.elasticsearch.articlequery.repo.book;


import com.elasticsearch.articlequery.models.vos.BookVO;
import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCustomRepo {
    /**
     * 通过查询title和introduction进行全文搜索
     * 并根据时间范围过滤
     *
     * @param queryStr
     * @return
     */
    public DataVO SearchByQueryStr(String queryStr, Pageable pageable);

    /**
     * 通过多个条件进行精准搜索
      * @param title
     * @param author
     * @param publish
     * @param gtScore
     * @param ltScore
     * @param tag
     * @param introduction
     * @param gtPrice
     * @param ltPrice
     * @param pageable
     * @return
     */
    public DataVO SearchByParams(String title, String author, String publish,
                                 String gtScore, String ltScore, String tag,
                                 String introduction, String gtPrice, String ltPrice,
                                 String isbn, Pageable pageable);

    /**
     * 根据出版社分类且计算总金额和平均评分
     *
     * @return
     */
    public List<BookVO> publishAgg(String publish);

    /**
     * 根据专业分类且计算总金额和平均评分
     *
     * @return
     */
    public List<BookVO> tagAgg(String type);

    /**
     * 根据发表时间做日期直方图聚合
     *
     * @return
     */
    public List<CommonVo> dateHistogramAgg(String interval);

}
