package com.example.restapi.repository;

import com.example.restapi.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long>, CustomLibraryRepository {
}
