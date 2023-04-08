package com.example.restapi.service;

import com.example.restapi.dto.LibrariesCountDTO;
import com.example.restapi.repository.LibraryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceTest {
    @Mock
    private LibraryRepository libraryRepository;
    @InjectMocks
    private LibraryService libraryService;

    @Before
    public void init() {
        when(this.libraryRepository.findLibrariesGroupByCountBooksDesc()).thenReturn(Arrays.asList(
                new LibrariesCountDTO(1L, "name1", 5L),
                new LibrariesCountDTO(2L, "name2", 6L),
                new LibrariesCountDTO(3L, "name3", 10L)
        ));

        when(this.libraryRepository.findLibrariesGroupByCountStudentReadersDesc()).thenReturn(Arrays.asList(
                new LibrariesCountDTO(1L, "name1", 5L),
                new LibrariesCountDTO(2L, "name2", 2L),
                new LibrariesCountDTO(3L, "name3", 1L)
        ));
    }

    @Test
    public void testGetLibrariesWithNumberOfBooksAsc() {
        List<LibrariesCountDTO> result = this.libraryService.getLibrariesWithNumberOfBooksDesc();

        assertEquals(3, result.size());
        assertEquals("name1", result.get(0).getName());
        assertEquals("name2", result.get(1).getName());
        assertEquals("name3", result.get(2).getName());
    }

    @Test
    public void testGetLibrariesGroupByCountStudentReadersDesc() {
        List<LibrariesCountDTO> result = this.libraryService.getLibrariesWithNumberOfStudentReadersDesc();

        assertEquals(3, result.size());
        assertEquals("name1", result.get(0).getName());
        assertEquals("name2", result.get(1).getName());
        assertEquals("name3", result.get(2).getName());
    }
}
