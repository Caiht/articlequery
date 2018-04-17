package com.elasticsearch.articlequery.repo.book;



import com.elasticsearch.articlequery.models.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepo extends BookCustomRepo, ElasticsearchRepository<Book, Long> {
}
