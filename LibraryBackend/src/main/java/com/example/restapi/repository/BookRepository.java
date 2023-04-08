package com.example.restapi.repository;

import com.example.restapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @NonNull
    Page<Book> findAll(@NonNull Pageable pageable);

    Page<Book> findByPriceGreaterThan(Double price, Pageable pageable);
    List<Book> findByLibraryID(Long id);
    @NonNull
    @EntityGraph(attributePaths = "library")
    Optional<Book> findById(@NonNull Long id);
}
