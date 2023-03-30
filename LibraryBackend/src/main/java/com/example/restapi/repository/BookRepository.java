package com.example.restapi.repository;

import com.example.restapi.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByPriceGreaterThan(Double price);
    List<Book> findByLibraryID(Long id);
}
