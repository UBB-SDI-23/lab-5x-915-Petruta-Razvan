package com.example.restapi.service;

import com.example.restapi.dto.BookDTO_onlyLibraryID;
import com.example.restapi.exceptions.BookNotFoundException;
import com.example.restapi.exceptions.LibraryNotFoundException;
import com.example.restapi.model.Book;
import com.example.restapi.model.Library;
import com.example.restapi.repository.BookRepository;
import com.example.restapi.repository.LibraryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;

    public BookService(BookRepository bookRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book addNewBook(Book newBook, Long id) {
        return this.libraryRepository.findById(id)
                .map(library -> {
                    newBook.setLibrary(library);
                    library.addBook(newBook);
                    return this.bookRepository.save(newBook);
                }).orElseThrow(() -> new LibraryNotFoundException(id));
    }

    public Book getBookById(Long id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book replaceBook(BookDTO_onlyLibraryID bookDTO, Long id) {
        Library library = this.libraryRepository.findById(bookDTO.getLibraryID()).orElseThrow(() -> new LibraryNotFoundException(bookDTO.getLibraryID()));
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        if (!Objects.equals(library.getID(), book.getLibrary().getID())) {
            book.getLibrary().removeBook(book);
            book.setLibrary(library);
            library.addBook(book);
        }

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setPrice(bookDTO.getPrice());
        book.setPublishedYear(bookDTO.getPublishedYear());
        return this.bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        Library library = this.libraryRepository.findById(book.getLibrary().getID()).orElseThrow(() -> new LibraryNotFoundException(book.getLibrary().getID()));

        library.removeBook(book);

        this.bookRepository.deleteById(id);
    }

    public List<Book> getBooksWithPriceGreater(Double price) {
        return this.bookRepository.findByPriceGreaterThan(price);
    }
}
