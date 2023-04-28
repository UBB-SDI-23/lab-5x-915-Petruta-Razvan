package com.example.restapi.controller;

import com.example.restapi.dtos.bookdtos.BookDTO_onlyLibraryID;
import com.example.restapi.dtos.librarydtos.LibrariesCountDTO;
import com.example.restapi.dtos.librarydtos.LibraryDTO_allBooks;
import com.example.restapi.dtos.librarydtos.LibraryDTO_noBooks;
import com.example.restapi.dtos.readerdtos.MembershipDTO;
import com.example.restapi.model.Library;
import com.example.restapi.model.membership.Membership;
import com.example.restapi.model.Reader;
import com.example.restapi.service.ILibraryService;
import com.example.restapi.service.IMembershipService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<LibraryDTO_noBooks>> allLibraries(@RequestParam(defaultValue = "0") Integer pageNo,
                                @RequestParam(defaultValue = "25") Integer pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.getAllLibraries(pageNo, pageSize));
    }

    @GetMapping("/libraries/{id}/books")
    ResponseEntity<List<BookDTO_onlyLibraryID>> allBookFromLibrary(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.getAllBooksFromLibrary(id));
    }

    @GetMapping("/libraries/{id}/readers")
    ResponseEntity<List<Reader>> allReadersFromLibrary(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.getReadersFromLibrary(id));
    }

    @GetMapping("/libraries/{id}")
    ResponseEntity<LibraryDTO_allBooks> oneLibrary(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.getLibraryById(id));
    }

    @GetMapping("/libraries/books-statistic")
    ResponseEntity<List<LibrariesCountDTO>> getLibrariesWithBooksStatistic() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.getLibrariesWithNumberOfBooksDesc());
    }

    @GetMapping("/libraries/readers-statistic")
    ResponseEntity<List<LibrariesCountDTO>> getLibrariesWithReadersStatistic() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.getLibrariesWithNumberOfStudentReadersDesc());
    }

    @GetMapping("/libraries/count")
    ResponseEntity<Long> countLibraries() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.countAllLibraries());
    }

    @GetMapping("/libraries-search")
    ResponseEntity<List<Library>> getLibrariesByName(@RequestParam(required = false) String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.searchLibrariesByNameFullText(name));
    }

    @PostMapping("/libraries")
    ResponseEntity<Library> newLibrary(@Valid @RequestBody Library newLibrary) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.libraryService.addNewLibrary(newLibrary));
    }

    @PostMapping("/libraries/{libraryID}/readers/{readerID}")
    ResponseEntity<Membership> newReaderMembership(@PathVariable Long libraryID, @PathVariable Long readerID) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.membershipService.createMembership(libraryID, readerID));
    }

    @PostMapping("/libraries/{id}/readersList")
    ResponseEntity<List<MembershipDTO>> newMembershipsList(@RequestBody List<MembershipDTO> memberships, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.membershipService.addNewMemberships(memberships, id));
    }

    @PutMapping("/libraries/{id}")
    ResponseEntity<Library> replaceLibrary(@Valid @RequestBody Library newLibrary, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.replaceLibrary(newLibrary, id));
    }

    @DeleteMapping("/libraries/{id}")
    ResponseEntity<HttpStatus> deleteLibrary(@PathVariable Long id) {
        this.libraryService.deleteLibrary(id);
        return ResponseEntity.accepted().body(HttpStatus.OK);
    }
}
