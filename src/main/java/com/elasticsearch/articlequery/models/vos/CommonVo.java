package com.elasticsearch.articlequery.models.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonVo {


    private String commonId;


    private Long count;
}
