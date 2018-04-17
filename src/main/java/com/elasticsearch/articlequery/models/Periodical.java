package com.elasticsearch.articlequery.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "periodical", type = "periodical", useServerConfiguration = true, createIndex = false)
public class Periodical {

    @Id
    private Long id;

    private String title;

    private String author;

    private String publisher;

    private Date date;

    private String type;

    private String introduction;
}
