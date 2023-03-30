package com.example.restapi.repository;

import com.example.restapi.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    List<Reader> findReadersByMembershipsLibraryID(Long libraryID);
}
