package com.example.restapi.service;

import com.example.restapi.dtos.bookdtos.BookDTO_onlyLibraryID;
import com.example.restapi.dtos.bookdtos.BookDTO_wholeLibrary;
import com.example.restapi.model.Book;

import java.util.List;

public interface IBookService {
    List<BookDTO_onlyLibraryID> getAllBooks(Integer pageNo, Integer pageSize);

    Book addNewBook(Book newBook, Long id);

    BookDTO_wholeLibrary getBookById(Long id);

    Book replaceBook(Book bookDTO, Long id);

    void deleteBook(Long id);

    List<BookDTO_onlyLibraryID> getBooksWithPriceGreater(Double price, Integer pageNo, Integer pageSize);

    long countAllBooks();

    long countBooksWithMinimumPrice(Double price);
}
