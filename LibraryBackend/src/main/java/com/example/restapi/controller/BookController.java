package com.example.restapi.controller;

import com.example.restapi.dto.BookDTO;
import com.example.restapi.dto.BookDTO_onlyLibraryID;
import com.example.restapi.dto.BookDTO_wholeLibrary;
import com.example.restapi.dto.DTOConverters;
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
    List<BookDTO_onlyLibraryID> allBooks(@RequestParam(required = false) Double minPrice,
                                         @RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "25") Integer pageSize) {
        if (minPrice == null) {
            return this.bookService.getAllBooks(pageNo, pageSize).stream().map(
                    (book) -> DTOConverters.convertToBookDTO_onlyLibraryID(book, this.modelMapper)
            ).collect(Collectors.toList());
        }
        return this.bookService.getBooksWithPriceGreater(minPrice, pageNo, pageSize).stream().map(
                (book) -> DTOConverters.convertToBookDTO_onlyLibraryID(book, this.modelMapper)
        ).collect(Collectors.toList());
    }

    @GetMapping("/booksWithMinimumPrice")
    List<BookDTO_onlyLibraryID> allBooksWithMinPrice(@RequestParam(required = false) Double minPrice,
                                                     @RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "100") Integer pageSize) {
        return this.bookService.getBooksWithPriceGreater(minPrice, pageNo, pageSize).stream().map(
                (book) -> DTOConverters.convertToBookDTO_onlyLibraryID(book, this.modelMapper)
        ).collect(Collectors.toList());
    }

    @GetMapping("/books/{id}")
    BookDTO_wholeLibrary oneBook(@PathVariable Long id) {
        return DTOConverters.convertToBookDTO_wholeLibrary(this.bookService.getBookById(id), modelMapper);
    }

    @GetMapping("/books/count")
    long countBooks() {
        return this.bookService.countAllBooks();
    }

    @PostMapping("/libraries/{id}/books")
    Book newBook(@Valid @RequestBody Book newBook, @PathVariable Long id) {
        return this.bookService.addNewBook(newBook, id);
    }

    @PutMapping("/books/{id}")
    Book replaceBook(@Valid @RequestBody Book book, @PathVariable Long id) {
        return this.bookService.replaceBook(book, id);
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
    }
}
