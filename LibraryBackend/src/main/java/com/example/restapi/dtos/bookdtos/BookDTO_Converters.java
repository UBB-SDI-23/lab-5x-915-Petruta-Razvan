package com.example.restapi.dtos.bookdtos;

import com.example.restapi.model.Book;
import org.modelmapper.ModelMapper;

public class BookDTO_Converters {
    public static BookDTO_onlyLibraryID convertToBookDTO_onlyLibraryID(Book book, ModelMapper modelMapper) {
        BookDTO_onlyLibraryID bookDTO = modelMapper.map(book, BookDTO_onlyLibraryID.class);
        bookDTO.setLibraryID(book.getLibrary().getID());
        return bookDTO;
    }

    public static BookDTO_wholeLibrary convertToBookDTO_wholeLibrary(Book book, ModelMapper modelMapper) {
        return modelMapper.map(book, BookDTO_wholeLibrary.class);
    }

    public static BookDTO_onlyLibraryID convertToBookDTO_forAll(Book book, ModelMapper modelMapper) {
        BookDTO_onlyLibraryID bookDTO = modelMapper.map(book, BookDTO_onlyLibraryID.class);
        bookDTO.setLibraryID(book.getLibrary().getID());
        return bookDTO;
    }
}
