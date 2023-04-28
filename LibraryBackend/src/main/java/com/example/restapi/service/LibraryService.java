package com.example.restapi.service;

import com.example.restapi.dtos.bookdtos.BookDTO_Converters;
import com.example.restapi.dtos.bookdtos.BookDTO_onlyLibraryID;
import com.example.restapi.dtos.librarydtos.LibrariesCountDTO;
import com.example.restapi.dtos.librarydtos.LibraryDTO_Converters;
import com.example.restapi.dtos.librarydtos.LibraryDTO_allBooks;
import com.example.restapi.dtos.librarydtos.LibraryDTO_noBooks;
import com.example.restapi.exceptions.LibraryNotFoundException;
import com.example.restapi.model.Library;
import com.example.restapi.model.Reader;
import com.example.restapi.repository.BookRepository;
import com.example.restapi.repository.library_repository.LibraryRepository;
import com.example.restapi.repository.MembershipRepository;
import com.example.restapi.repository.ReaderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService implements ILibraryService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final ReaderRepository readerRepository;
    private final MembershipRepository membershipRepository;
    private final ModelMapper modelMapper;

    public LibraryService(BookRepository bookRepository, LibraryRepository libraryRepository, ReaderRepository readerRepository, MembershipRepository membershipRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.readerRepository = readerRepository;
        this.membershipRepository = membershipRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LibraryDTO_noBooks> getAllLibraries(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("ID"));

        return this.libraryRepository.findAll(pageable).getContent().stream().map(
                library -> LibraryDTO_Converters.convertToLibraryDTO_noBooks(library,
                        this.membershipRepository.countByLibraryID(library.getID()),
                        this.bookRepository.countByLibraryID(library.getID()))
        ).collect(Collectors.toList());
    }

    @Override
    public long countAllLibraries() {
        return this.libraryRepository.count();
    }

    @Override
    public List<BookDTO_onlyLibraryID> getAllBooksFromLibrary(Long id) {
        return this.bookRepository.findByLibraryID(id).stream().map(
                (book) -> BookDTO_Converters.convertToBookDTO_forAll(book, this.modelMapper)
        ).collect(Collectors.toList());
    }

    @Override
    public Library addNewLibrary(Library newLibrary) {
        return this.libraryRepository.save(newLibrary);
    }

    @Override
    public LibraryDTO_allBooks getLibraryById(Long id) {
        return LibraryDTO_Converters.convertToLibraryDTO_forOne(this.libraryRepository.findById(id).orElseThrow(() ->
                new LibraryNotFoundException(id)), modelMapper, readerRepository);
    }

    @Override
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

    @Override
    public void deleteLibrary(Long id) {
        this.libraryRepository.deleteById(id);
    }

    @Override
    public List<Reader> getReadersFromLibrary(Long libraryID) {
        return this.readerRepository.findReadersByMembershipsLibraryID(libraryID);
    }

    @Override
    public List<LibrariesCountDTO> getLibrariesWithNumberOfBooksDesc() {
        return this.libraryRepository.findLibrariesGroupByCountBooksDesc();
    }

    @Override
    public List<LibrariesCountDTO> getLibrariesWithNumberOfStudentReadersDesc() {
        return this.libraryRepository.findLibrariesGroupByCountStudentReadersDesc();
    }

    @Override
    public List<Library> searchLibrariesByNameFullText(String name) {
        return this.libraryRepository.findTop20BySearchTerm(name);
    }
}
