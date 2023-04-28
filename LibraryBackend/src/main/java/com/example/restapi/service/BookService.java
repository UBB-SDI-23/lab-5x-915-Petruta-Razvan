package com.example.restapi.service;

import com.example.restapi.dtos.bookdtos.BookDTO_Converters;
import com.example.restapi.dtos.bookdtos.BookDTO_onlyLibraryID;
import com.example.restapi.dtos.bookdtos.BookDTO_wholeLibrary;
import com.example.restapi.exceptions.BookNotFoundException;
import com.example.restapi.exceptions.LibraryNotFoundException;
import com.example.restapi.model.Book;
import com.example.restapi.model.Library;
import com.example.restapi.repository.BookRepository;
import com.example.restapi.repository.library_repository.LibraryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final ModelMapper modelMapper;

    public BookService(BookRepository bookRepository, LibraryRepository libraryRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.libraryRepository = libraryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookDTO_onlyLibraryID> getAllBooks(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("ID"));

        return this.bookRepository.findAll(pageable).getContent().stream().map(
                (book) -> BookDTO_Converters.convertToBookDTO_onlyLibraryID(book, this.modelMapper)
        ).collect(Collectors.toList());
    }

    @Override
    public Book addNewBook(Book newBook, Long id) {
        return this.libraryRepository.findById(id)
                .map(library -> {
                    newBook.setLibrary(library);
                    library.addBook(newBook);
                    return this.bookRepository.save(newBook);
                }).orElseThrow(() -> new LibraryNotFoundException(id));
    }

    @Override
    public BookDTO_wholeLibrary getBookById(Long id) {
        return BookDTO_Converters.convertToBookDTO_wholeLibrary(this.bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException(id)), this.modelMapper);
    }

    @Override
    public Book replaceBook(Book bookDTO, Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPublisher(bookDTO.getPublisher());
        book.setPrice(bookDTO.getPrice());
        book.setPublishedYear(bookDTO.getPublishedYear());
        book.setDescription(bookDTO.getDescription());
        return this.bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        Library library = this.libraryRepository.findById(book.getLibrary().getID()).orElseThrow(() -> new LibraryNotFoundException(book.getLibrary().getID()));

        library.removeBook(book);

        this.bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO_onlyLibraryID> getBooksWithPriceGreater(Double price, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("price"));

        return this.bookRepository.findByPriceGreaterThanEqual(price, pageable).getContent().stream().map(
                (book) -> BookDTO_Converters.convertToBookDTO_onlyLibraryID(book, this.modelMapper)
        ).collect(Collectors.toList());
    }

    @Override
    public long countAllBooks() {
        return this.bookRepository.count();
    }

    @Override
    public long countBooksWithMinimumPrice(Double price) {
        return this.bookRepository.countByPriceGreaterThanEqual(price);
    }
}
