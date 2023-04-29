package com.example.restapi.controller;

import com.example.restapi.dtos.readerdtos.ReaderDTO_forAll;
import com.example.restapi.dtos.readerdtos.ReaderDTO_forOne;
import com.example.restapi.model.Reader;
import com.example.restapi.model.user.User;
import com.example.restapi.security.jwt.JwtUtils;
import com.example.restapi.service.reader_service.IReaderService;

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
public class ReaderController {
    private final IReaderService readerService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    public ReaderController(IReaderService readerService, UserService userService, JwtUtils jwtUtils) {
        this.readerService = readerService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/readers")
    ResponseEntity<List<ReaderDTO_forAll>> allReaders(@RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "25") Integer pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.readerService.getAllReaders(pageNo, pageSize));
    }

    @GetMapping("/readers/count")
    ResponseEntity<Long> countReaders() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.readerService.countAllReaders());
    }

    @GetMapping("/readers/{id}")
    ResponseEntity<ReaderDTO_forOne> oneReader(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.readerService.getReaderById(id));
    }

    @PostMapping("/readers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    ResponseEntity<Reader> newReader(@Valid @RequestBody Reader newReader, HttpServletRequest request) {
        String token = this.jwtUtils.getJwtFromCookies(request);
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.readerService.addNewReader(newReader, user.getID()));
    }

    @PutMapping("/readers/{readerID}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    ResponseEntity<Reader> replaceReader(@Valid @RequestBody Reader newReader, @PathVariable Long readerID, HttpServletRequest request) {
        String token = this.jwtUtils.getJwtFromCookies(request);
        String username = this.jwtUtils.getUserNameFromJwtToken(token);
        User user = this.userService.getUserByUsername(username);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.readerService.replaceReader(newReader, readerID, user.getID()));
    }

    @DeleteMapping("/readers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<HttpStatus> deleteReader(@PathVariable Long id) {
        this.readerService.deleteReader(id);
        return ResponseEntity.accepted().body(HttpStatus.OK);
    }
}
