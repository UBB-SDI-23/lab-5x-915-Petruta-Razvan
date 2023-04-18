package com.example.restapi.controller;

import com.example.restapi.dtos.bookdtos.BookDTO_onlyLibraryID;
import com.example.restapi.dtos.librarydtos.LibrariesCountDTO;
import com.example.restapi.dtos.librarydtos.LibraryDTO_allBooks;
import com.example.restapi.dtos.librarydtos.LibraryDTO_noBooks;
import com.example.restapi.dtos.readerdtos.MembershipDTO;
import com.example.restapi.model.Library;
import com.example.restapi.model.Membership;
import com.example.restapi.model.Reader;
import com.example.restapi.service.ILibraryService;
import com.example.restapi.service.IMembershipService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class LibraryController {
    private final ILibraryService libraryService;
    private final IMembershipService membershipService;

    public LibraryController(ILibraryService libraryService, IMembershipService membershipService) {
        this.libraryService = libraryService;
        this.membershipService = membershipService;
    }


    // LIBRARIES --------------------------------------------------------
    @GetMapping("/libraries")
    List<LibraryDTO_noBooks> allLibraries(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "25") Integer pageSize) {
        return this.libraryService.getAllLibraries(pageNo, pageSize);
    }

    @GetMapping("/libraries/{id}/books")
    List<BookDTO_onlyLibraryID> allBookFromLibrary(@PathVariable Long id) {
        return this.libraryService.getAllBooksFromLibrary(id);
    }

    @GetMapping("/libraries/{id}/readers")
    List<Reader> allReadersFromLibrary(@PathVariable Long id) {
        return this.libraryService.getReadersFromLibrary(id);
    }

    @GetMapping("/libraries/{id}")
    LibraryDTO_allBooks oneLibrary(@PathVariable Long id) {
        return this.libraryService.getLibraryById(id);
    }

    @GetMapping("/libraries/books-statistic")
    List<LibrariesCountDTO> getLibrariesWithBooksStatistic() {
        return this.libraryService.getLibrariesWithNumberOfBooksDesc();
    }

    @GetMapping("/libraries/readers-statistic")
    List<LibrariesCountDTO> getLibrariesWithReadersStatistic() {
        return this.libraryService.getLibrariesWithNumberOfStudentReadersDesc();
    }

    @GetMapping("/libraries/count")
    long countLibraries() {
        return this.libraryService.countAllLibraries();
    }

    @GetMapping("/libraries-search")
    List<Library> getLibrariesByName(@RequestParam(required = false) String name) {
        return this.libraryService.searchLibrariesByNameFullText(name);
    }

    @PostMapping("/libraries")
    Library newLibrary(@Valid @RequestBody Library newLibrary) {
        return this.libraryService.addNewLibrary(newLibrary);
    }

    @PostMapping("/libraries/{libraryID}/readers/{readerID}")
    Membership newReaderMembership(@PathVariable Long libraryID, @PathVariable Long readerID) {
        return this.membershipService.createMembership(libraryID, readerID);
    }

    @PostMapping("/libraries/{id}/readersList")
    List<MembershipDTO> newMembershipsList(@RequestBody List<MembershipDTO> memberships, @PathVariable Long id) {
        return this.membershipService.addNewMemberships(memberships, id);
    }

    @PutMapping("/libraries/{id}")
    Library replaceLibrary(@Valid @RequestBody Library newLibrary, @PathVariable Long id) {
        return this.libraryService.replaceLibrary(newLibrary, id);
    }

    @DeleteMapping("/libraries/{id}")
    void deleteLibrary(@PathVariable Long id) {
        this.libraryService.deleteLibrary(id);
    }
}
