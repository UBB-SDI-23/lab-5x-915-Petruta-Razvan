package com.example.restapi.repository;

import com.example.restapi.dto.LibrariesCountDTO;

import java.util.List;

public interface CustomLibraryRepository {
    List<LibrariesCountDTO> findLibrariesGroupByCountBooksAsc();

    List<LibrariesCountDTO> findLibrariesGroupByCountStudentReadersDesc();
}
