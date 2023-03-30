package com.example.restapi.controller;

import com.example.restapi.dto.LibraryDTO_withMembership;
import com.example.restapi.dto.ReaderDTO;
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
        // get all the readers
    List<ReaderDTO> allReaders() {
        return this.readerService.getAllReaders().stream().map(this::convertToReaderDTO_forAll).collect(Collectors.toList());
    }

    @PostMapping("/readers")
        // add a new reader
    Reader newReader(@Valid @RequestBody Reader newReader) {
        return this.readerService.addNewReader(newReader);
    }

    @GetMapping("/readers/{id}")
        // get a reader by id
    ReaderDTO oneReader(@PathVariable Long id) {
        return this.convertToReaderDTO_forOne(this.readerService.getReaderById(id));
    }

    @PutMapping("/readers/{id}")
        // update a reader given the id
    Reader replaceReader(@Valid @RequestBody Reader newReader, @PathVariable Long id) {
        return this.readerService.replaceReader(newReader, id);
    }

    @DeleteMapping("/readers/{id}")
        // delete a library by id
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

    private ReaderDTO_forAll convertToReaderDTO_forAll(Reader reader) {
        return this.modelMapper.map(reader, ReaderDTO_forAll.class);
    }
}
