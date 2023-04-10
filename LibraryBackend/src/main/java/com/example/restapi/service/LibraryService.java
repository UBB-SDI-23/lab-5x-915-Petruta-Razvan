package com.example.restapi.service;

import com.example.restapi.dto.LibrariesCountDTO;
import com.example.restapi.exceptions.LibraryNotFoundException;
import com.example.restapi.model.Book;
import com.example.restapi.model.Library;
import com.example.restapi.model.Reader;
import com.example.restapi.repository.BookRepository;
import com.example.restapi.repository.LibraryRepository;
import com.example.restapi.repository.ReaderRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final ReaderRepository readerRepository;

    public LibraryService(BookRepository bookRepository, LibraryRepository libraryRepository, ReaderRepository readerRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.readerRepository = readerRepository;
    }

    public List<Library> getAllLibraries(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("ID"));

        return this.libraryRepository.findAll(pageable).getContent();
    }

    public long countAllLibraries() {
        return this.libraryRepository.count();
    }

    public List<Book> getAllBooksFromLibrary(Long id) {
        return this.bookRepository.findByLibraryID(id);
    }

    public Library addNewLibrary(Library newLibrary) {
        return this.libraryRepository.save(newLibrary);
    }

    public Library getLibraryById(Long id) {
        return this.libraryRepository.findById(id).orElseThrow(() -> new LibraryNotFoundException(id));
    }

    public Library replaceLibrary(Library newLibrary, Long id) {
        return this.libraryRepository.findById(id)
                .map(library -> {
                    library.setName(newLibrary.getName());
                    library.setEmail(newLibrary.getEmail());
                    library.setAddress(newLibrary.getAddress());
                    library.setWebsite(newLibrary.getWebsite());
                    library.setYearOfConstruction(newLibrary.getYearOfConstruction());
                    return this.libraryRepository.save(library);
                })
                .orElseGet(() -> {
                    newLibrary.setID(id);
                    return this.libraryRepository.save(newLibrary);
                });
    }

    public void deleteLibrary(Long id) {
        this.libraryRepository.deleteById(id);
    }

    public List<Reader> getReadersFromLibrary(Long libraryID) {
        return this.readerRepository.findReadersByMembershipsLibraryID(libraryID);
    }

    public List<LibrariesCountDTO> getLibrariesWithNumberOfBooksDesc() {
        return this.libraryRepository.findLibrariesGroupByCountBooksDesc();
    }

    public List<LibrariesCountDTO> getLibrariesWithNumberOfStudentReadersDesc() {
        return this.libraryRepository.findLibrariesGroupByCountStudentReadersDesc();
    }
}
