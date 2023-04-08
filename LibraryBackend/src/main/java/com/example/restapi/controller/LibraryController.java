package com.example.restapi.controller;

import com.example.restapi.dto.*;
import com.example.restapi.model.Book;
import com.example.restapi.model.Library;
import com.example.restapi.model.Membership;
import com.example.restapi.model.Reader;
import com.example.restapi.service.LibraryService;
import com.example.restapi.service.MembershipService;
import com.example.restapi.service.ReaderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class LibraryController {
    private final LibraryService libraryService;
    private final MembershipService membershipService;
    private final ReaderService readerService;
    private final ModelMapper modelMapper;

    public LibraryController(LibraryService libraryService, MembershipService membershipService, ReaderService readerService, ModelMapper modelMapper) {
        this.libraryService = libraryService;
        this.membershipService = membershipService;
        this.readerService = readerService;
        this.modelMapper = modelMapper;
    }


    // LIBRARIES --------------------------------------------------------
    @GetMapping("/libraries")
    // get all the libraries
    List<LibraryDTO_noBooks> allLibraries(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "100") Integer pageSize) {
        return this.libraryService.getAllLibraries(pageNo, pageSize).stream().map(this::convertToLibraryDTO_forAll).collect(Collectors.toList());
    }

    @GetMapping("/libraries/{id}/books")
    // get all the books from a library given the id
    List<BookDTO_onlyLibraryID> allBookFromLibrary(@PathVariable Long id) {
        return this.libraryService.getAllBooksFromLibrary(id).stream().map(this::convertToBookDTO_forAll).collect(Collectors.toList());
    }

    @GetMapping("/libraries/{id}/readers")
    List<Reader> allReadersFromLibrary(@PathVariable Long id) {
        return this.libraryService.getReadersFromLibrary(id);
    }

    @GetMapping("/libraries/{id}")
    // get a library by id
    LibraryDTO_allBooks oneLibrary(@PathVariable Long id) {
        return this.convertToLibraryDTO_forOne(this.libraryService.getLibraryById(id));
    }

    @GetMapping("/libraries/books-statistic")
    List<LibrariesCountDTO> getLibrariesWithBooksStatistic() {
        return this.libraryService.getLibrariesWithNumberOfBooksDesc();
    }

    @GetMapping("/libraries/readers-statistic")
    List<LibrariesCountDTO> getLibrariesWithReadersStatistic() {
        return this.libraryService.getLibrariesWithNumberOfStudentReadersDesc();
    }

    @PostMapping("/libraries")
    // add a new library
    Library newLibrary(@Valid @RequestBody Library newLibrary) {
        return this.libraryService.addNewLibrary(newLibrary);
    }

    @PostMapping("/libraries/{id}/readers")
    Membership newReaderMembership(@RequestBody Reader reader, @PathVariable Long id) {
        return this.membershipService.createMembership(id, reader);
    }

    @PostMapping("/libraries/{id}/readersList")
    List<MembershipDTO> newMembershipsList(@RequestBody List<MembershipDTO> memberships, @PathVariable Long id) {
        return this.membershipService.addNewMemberships(memberships, id);
    }

    @PutMapping("/libraries/{id}")
    // update a library given the id
    Library replaceLibrary(@Valid @RequestBody Library newLibrary, @PathVariable Long id) {
        return this.libraryService.replaceLibrary(newLibrary, id);
    }

    @DeleteMapping("/libraries/{id}")
    // delete a library by id
    void deleteLibrary(@PathVariable Long id) {
        this.libraryService.deleteLibrary(id);
    }

    private LibraryDTO_noBooks convertToLibraryDTO_forAll(Library library) {
        return this.modelMapper.map(library, LibraryDTO_noBooks.class);
    }

    private LibraryDTO_allBooks convertToLibraryDTO_forOne(Library library) {
        LibraryDTO_allBooks libraryDTO = this.modelMapper.map(library, LibraryDTO_allBooks.class);
        Set<ReaderDTO_withMembership> readers = library.getMemberships().stream()
                .map(membership -> {
                    Reader reader = this.readerService.getReaderById(membership.getID().getReaderID());
                    ReaderDTO_withMembership readerDTO = new ReaderDTO_withMembership();
                    readerDTO.setID(reader.getID());
                    readerDTO.setName(reader.getName());
                    readerDTO.setEmail(reader.getEmail());
                    readerDTO.setGender(reader.getGender());
                    readerDTO.setStudent(reader.isStudent());
                    readerDTO.setBirthDate(reader.getBirthDate());
                    readerDTO.setStartDate(membership.getStartDate());
                    readerDTO.setEndDate(membership.getEndDate());
                    return readerDTO;
                }).collect(Collectors.toSet());
        libraryDTO.setReaders(readers);
        return libraryDTO;
    }

    private BookDTO_onlyLibraryID convertToBookDTO_forAll(Book book) {
        BookDTO_onlyLibraryID bookDTO = this.modelMapper.map(book, BookDTO_onlyLibraryID.class);
        bookDTO.setLibraryID(book.getLibrary().getID());
        return bookDTO;
    }
}
