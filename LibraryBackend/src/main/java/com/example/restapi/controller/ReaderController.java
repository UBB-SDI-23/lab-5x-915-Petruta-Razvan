package com.example.restapi.controller;

import com.example.restapi.dtos.readerdtos.ReaderDTO_forAll;
import com.example.restapi.dtos.readerdtos.ReaderDTO_forOne;
import com.example.restapi.model.Reader;
import com.example.restapi.service.IReaderService;
import jakarta.validation.Valid;
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
    List<ReaderDTO_forAll> allReaders(@RequestParam(defaultValue = "0") Integer pageNo,
                                      @RequestParam(defaultValue = "25") Integer pageSize) {
        return this.readerService.getAllReaders(pageNo, pageSize);
    }

    @GetMapping("/readers/count")
    long countReaders() {
        return this.readerService.countAllReaders();
    }

    @GetMapping("/readers/{id}")
    ReaderDTO_forOne oneReader(@PathVariable Long id) {
        return this.readerService.getReaderById(id);
    }

    @PostMapping("/readers")
    Reader newReader(@Valid @RequestBody Reader newReader) {
        return this.readerService.addNewReader(newReader);
    }

    @PutMapping("/readers/{id}")
    Reader replaceReader(@Valid @RequestBody Reader newReader, @PathVariable Long id) {
        return this.readerService.replaceReader(newReader, id);
    }

    @DeleteMapping("/readers/{id}")
    void deleteReader(@PathVariable Long id) {
        this.readerService.deleteReader(id);
    }
}
