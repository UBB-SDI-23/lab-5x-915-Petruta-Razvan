package com.example.restapi.controller;

import com.example.restapi.dto.BookDTO;
import com.example.restapi.dto.BookDTO_onlyLibraryID;
import com.example.restapi.dto.BookDTO_wholeLibrary;
import com.example.restapi.model.Book;
import com.example.restapi.service.BookService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class BookController {
    private final BookService bookService;
    private final ModelMapper modelMapper;

    public BookController(BookService bookService, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/books")
    // get all the books, or filter by price if there is a parameter specified
    List<BookDTO> allBooks(@RequestParam(required = false) Double minPrice) {
        if (minPrice == null) {
            return this.bookService.getAllBooks().stream().map(this::convertToBookDTO_onlyLibraryID).collect(Collectors.toList());
        }
        return this.bookService.getBooksWithPriceGreater(minPrice).stream().map(this::convertToBookDTO_onlyLibraryID).collect(Collectors.toList());
    }

    @GetMapping("/booksWithMinimumPrice")
        // get all the books, or filter by price if there is a parameter specified
    List<BookDTO> allBooksWithMinPrice(@RequestParam(required = false) Double minPrice) {
        return this.bookService.getBooksWithPriceGreater(minPrice).stream().map(this::convertToBookDTO_onlyLibraryID).collect(Collectors.toList());
    }

    @GetMapping("/books/{id}")
    // get a book by its id
    BookDTO oneBook(@PathVariable Long id) {
        return this.convertToBookDTO_wholeLibrary(this.bookService.getBookById(id));
    }

    @PostMapping("/libraries/{id}/books")
    // add a new book in an existent library
    Book newBook(@Valid @RequestBody Book newBook, @PathVariable Long id) {
        return this.bookService.addNewBook(newBook, id);
    }

    @PutMapping("/books/{id}")
    // update a book given the id
    Book replaceBook(@Valid @RequestBody BookDTO_onlyLibraryID bookDTO, @PathVariable Long id) {
        return this.bookService.replaceBook(bookDTO, id);
    }

    @DeleteMapping("/books/{id}")
    // delete a book by its id
    void deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
    }

    private BookDTO convertToBookDTO_onlyLibraryID(Book book) {
        BookDTO_onlyLibraryID bookDTO = this.modelMapper.map(book, BookDTO_onlyLibraryID.class);
        bookDTO.setLibraryID(book.getLibrary().getID());
        return bookDTO;
    }

    private BookDTO convertToBookDTO_wholeLibrary(Book book) {
        return this.modelMapper.map(book, BookDTO_wholeLibrary.class);
    }
}
