package com.elasticsearch.articlequery.models.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVO {


    private Long total;


    private List<?> list;
}
