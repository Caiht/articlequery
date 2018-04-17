package com.elasticsearch.articlequery.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "patent", type = "patent", useServerConfiguration = true, createIndex = false)
public class Patent {
    @Id
    private Long id;

    private String title;

    private String requestNumber;

    private Date requestDate;

    private String publicationNumber;

    private Date publicationDate;

    private String proposer;

    private String inventor;

    private String introduction;

    private String type;
}
