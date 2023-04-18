package com.example.restapi.repository;

import com.example.restapi.dtos.librarydtos.LibrariesCountDTO;

import java.util.List;

public interface CustomLibraryRepository {
    List<LibrariesCountDTO> findLibrariesGroupByCountBooksDesc();

    List<LibrariesCountDTO> findLibrariesGroupByCountStudentReadersDesc();
}
