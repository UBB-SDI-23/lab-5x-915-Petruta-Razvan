package com.example.restapi.controller;

import com.example.restapi.dtos.bookdtos.BookDTO_onlyLibraryID;
import com.example.restapi.dtos.librarydtos.LibrariesCountDTO;
import com.example.restapi.dtos.librarydtos.LibraryDTO_allBooks;
import com.example.restapi.dtos.librarydtos.LibraryDTO_noBooks;
import com.example.restapi.dtos.readerdtos.MembershipDTO;
import com.example.restapi.model.Library;
import com.example.restapi.model.membership.Membership;
import com.example.restapi.model.Reader;
import com.example.restapi.model.user.User;
import com.example.restapi.security.jwt.JwtUtils;
import com.example.restapi.service.library_service.ILibraryService;
import com.example.restapi.service.membership_service.IMembershipService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.restapi.service.user_service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public LibraryController(ILibraryService libraryService, IMembershipService membershipService, UserService userService, JwtUtils jwtUtils) {
        this.libraryService = libraryService;
        this.membershipService = membershipService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    ResponseEntity<Library> newLibrary(@Valid @RequestBody Library newLibrary, HttpServletRequest request) {
        String token = this.jwtUtils.getJwtFromCookies(request);
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.libraryService.addNewLibrary(newLibrary, user.getID()));
    }

    @PostMapping("/libraries/{libraryID}/readers/{readerID}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    ResponseEntity<Membership> newReaderMembership(@PathVariable Long libraryID, @PathVariable Long readerID, HttpServletRequest request) {
        String token = this.jwtUtils.getJwtFromCookies(request);
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.membershipService.createMembership(libraryID, readerID, user.getID()));
    }

    @PostMapping("/libraries/{id}/readersList")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    ResponseEntity<List<MembershipDTO>> newMembershipsList(@RequestBody List<MembershipDTO> memberships, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.membershipService.addNewMemberships(memberships, id));
    }

    @PutMapping("/libraries/{libraryID}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    ResponseEntity<Library> replaceLibrary(@Valid @RequestBody Library newLibrary, @PathVariable Long libraryID, HttpServletRequest request) {
        String token = this.jwtUtils.getJwtFromCookies(request);
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.libraryService.replaceLibrary(newLibrary, libraryID, user.getID()));
    }

    @DeleteMapping("/libraries/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<HttpStatus> deleteLibrary(@PathVariable Long id) {
        this.libraryService.deleteLibrary(id);
        return ResponseEntity.accepted().body(HttpStatus.OK);
    }
}
