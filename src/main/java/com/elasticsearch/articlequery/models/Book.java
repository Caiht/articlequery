package com.elasticsearch.articlequery.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "book", type = "book", useServerConfiguration = true, createIndex = false)
public class Book {

    @Id
    private Long id;

    private String title;

    private Double score;

    private String author;

    private Double price;

    private Date date;

    private String publish;

    private String person;

    private String tag;

    private String introduction;

    private String isbn;
}
