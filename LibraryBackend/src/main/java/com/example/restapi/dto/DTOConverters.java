package com.example.restapi.dto;

import com.example.restapi.model.Book;
import com.example.restapi.model.Library;
import com.example.restapi.model.Reader;
import org.modelmapper.ModelMapper;


public class DTOConverters {
    public static BookDTO_onlyLibraryID convertToBookDTO_onlyLibraryID(Book book, ModelMapper modelMapper) {
        BookDTO_onlyLibraryID bookDTO = modelMapper.map(book, BookDTO_onlyLibraryID.class);
        bookDTO.setLibraryID(book.getLibrary().getID());
        return bookDTO;
    }

    public static BookDTO_wholeLibrary convertToBookDTO_wholeLibrary(Book book, ModelMapper modelMapper) {
        return modelMapper.map(book, BookDTO_wholeLibrary.class);
    }

    public static LibraryDTO_noBooks convertToLibraryDTO_forAll(Library library, ModelMapper modelMapper) {
        return modelMapper.map(library, LibraryDTO_noBooks.class);
    }

    public static BookDTO_onlyLibraryID convertToBookDTO_forAll(Book book, ModelMapper modelMapper) {
        BookDTO_onlyLibraryID bookDTO = modelMapper.map(book, BookDTO_onlyLibraryID.class);
        bookDTO.setLibraryID(book.getLibrary().getID());
        return bookDTO;
    }

    public static ReaderDTO_forAll convertToReaderDTO_forAll(Reader reader, ModelMapper modelMapper) {
        return modelMapper.map(reader, ReaderDTO_forAll.class);
    }
}
