package com.elasticsearch.articlequery.models.vos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVO {


    private String title;


    private BigDecimal priceSum;


    private Long count;


    private BigDecimal scoreAvg;

}
