package com.example.restapi.repository;

import com.example.restapi.model.Library;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long>, CustomLibraryRepository {
    @NonNull
    Page<Library> findAll(@NonNull Pageable pageable);
}
