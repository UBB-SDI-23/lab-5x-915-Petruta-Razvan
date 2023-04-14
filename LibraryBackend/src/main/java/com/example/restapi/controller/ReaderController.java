package com.example.restapi.controller;

import com.example.restapi.dto.LibraryDTO_withMembership;
import com.example.restapi.dto.ReaderDTO_forAll;
import com.example.restapi.dto.ReaderDTO_forOne;
import com.example.restapi.model.Library;
import com.example.restapi.model.Reader;
import com.example.restapi.service.LibraryService;
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
public class ReaderController {
    private final ReaderService readerService;
    private final LibraryService libraryService;
    private final ModelMapper modelMapper;

    public ReaderController(ReaderService readerService, LibraryService libraryService, ModelMapper modelMapper) {
        this.readerService = readerService;
        this.libraryService = libraryService;
        this.modelMapper = modelMapper;
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

    @PostMapping("/readers")
    Reader newReader(@Valid @RequestBody Reader newReader) {
        return this.readerService.addNewReader(newReader);
    }

    @GetMapping("/readers/{id}")
    ReaderDTO_forOne oneReader(@PathVariable Long id) {
        return this.convertToReaderDTO_forOne(this.readerService.getReaderById(id));
    }

    @PutMapping("/readers/{id}")
    Reader replaceReader(@Valid @RequestBody Reader newReader, @PathVariable Long id) {
        return this.readerService.replaceReader(newReader, id);
    }

    @DeleteMapping("/readers/{id}")
    void deleteReader(@PathVariable Long id) {
        this.readerService.deleteReader(id);
    }

    private ReaderDTO_forOne convertToReaderDTO_forOne(Reader reader) {
        ReaderDTO_forOne readerDTO = this.modelMapper.map(reader, ReaderDTO_forOne.class);
        Set<LibraryDTO_withMembership> libraries = reader.getMemberships().stream()
                .map((membership -> {
                    Library library = this.libraryService.getLibraryById(membership.getID().getLibraryID());
                    LibraryDTO_withMembership libraryDTO = new LibraryDTO_withMembership();
                    libraryDTO.setID(library.getID());
                    libraryDTO.setName(library.getName());
                    libraryDTO.setEmail(library.getEmail());
                    libraryDTO.setAddress(library.getAddress());
                    libraryDTO.setWebsite(library.getWebsite());
                    libraryDTO.setYearOfConstruction(library.getYearOfConstruction());
                    libraryDTO.setStartDate(membership.getStartDate());
                    libraryDTO.setEndDate(membership.getEndDate());
                    return libraryDTO;
                })).collect(Collectors.toSet());
        readerDTO.setLibraries(libraries);
        return readerDTO;
    }
}
