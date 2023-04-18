package com.example.restapi.controller;

import com.example.restapi.dtos.readerdtos.ReaderDTO_forAll;
import com.example.restapi.dtos.readerdtos.ReaderDTO_forOne;
import com.example.restapi.model.Reader;
import com.example.restapi.service.IReaderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class ReaderController {
    private final IReaderService readerService;

    public ReaderController(IReaderService readerService) {
        this.readerService = readerService;
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
    ResponseEntity<Reader> newReader(@Valid @RequestBody Reader newReader) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.readerService.addNewReader(newReader));
    }

    @PutMapping("/readers/{id}")
    ResponseEntity<Reader> replaceReader(@Valid @RequestBody Reader newReader, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.readerService.replaceReader(newReader, id));
    }

    @DeleteMapping("/readers/{id}")
    ResponseEntity<HttpStatus> deleteReader(@PathVariable Long id) {
        this.readerService.deleteReader(id);
        return ResponseEntity.accepted().body(HttpStatus.OK);
    }
}
