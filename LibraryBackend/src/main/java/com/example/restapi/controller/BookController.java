package com.example.restapi.controller;

import com.example.restapi.dtos.bookdtos.BookDTO_onlyLibraryID;
import com.example.restapi.dtos.bookdtos.BookDTO_wholeLibrary;
import com.example.restapi.model.Book;
import com.example.restapi.service.IBookService;
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
public class BookController {
    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    ResponseEntity<List<BookDTO_onlyLibraryID>> allBooks(@RequestParam(required = false) Double minPrice,
                            @RequestParam(defaultValue = "0") Integer pageNo,
                            @RequestParam(defaultValue = "25") Integer pageSize) {
        if (minPrice == null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.bookService.getAllBooks(pageNo, pageSize));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.bookService.getBooksWithPriceGreater(minPrice, pageNo, pageSize));
    }

    @GetMapping("/books/{id}")
    ResponseEntity<BookDTO_wholeLibrary> oneBook(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.bookService.getBookById(id));
    }

    @GetMapping("/books/count")
    ResponseEntity<Long> countBooks() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.bookService.countAllBooks());
    }

    @GetMapping("/books/countByPrice")
    ResponseEntity<Long> countBooksWithMinPrice(@RequestParam Double minPrice) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.bookService.countBooksWithMinimumPrice(minPrice));
    }

    @PostMapping("/libraries/{id}/books")
    ResponseEntity<Book> newBook(@Valid @RequestBody Book newBook, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.bookService.addNewBook(newBook, id));
    }

    @PutMapping("/books/{id}")
    ResponseEntity<Book> replaceBook(@Valid @RequestBody Book book, @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.bookService.replaceBook(book, id));
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.accepted().body(HttpStatus.OK);
    }
}
