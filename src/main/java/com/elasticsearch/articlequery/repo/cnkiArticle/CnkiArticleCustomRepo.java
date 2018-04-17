package com.elasticsearch.articlequery.repo.cnkiArticle;


import com.elasticsearch.articlequery.models.vos.CommonVo;
import com.elasticsearch.articlequery.models.vos.DataVO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CnkiArticleCustomRepo {
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
     * 并根据时间范围过滤
     *
     * @param title
     * @param author
     * @param teacher
     * @param university
     * @param type
     * @param introduction
     * @return
     */
    public DataVO SearchByParams(String title, String author, String teacher,
                                                        String university, String type, String introduction,
                                                        String gtDate, String ltDate, Pageable pageable);

    /**根据学校分类
     * @return
     */
    public List<CommonVo> universityAgg(String university);

    /**根据专业分类
     * @return
     */
    public List<CommonVo> typeAgg(String type);

    /**根据发表时间做日期直方图聚合
     * @return
     */
    public List<CommonVo> dateHistogramAgg(String interval);

}
