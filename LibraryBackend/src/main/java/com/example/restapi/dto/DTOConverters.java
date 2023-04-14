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

    public static LibraryDTO_noBooks convertToLibraryDTO_noBooks(Library library, Long countReaders, Long countBooks) {
        LibraryDTO_noBooks newLibrary = new LibraryDTO_noBooks();
        newLibrary.setID(library.getID());
        newLibrary.setName(library.getName());
        newLibrary.setEmail(library.getEmail());
        newLibrary.setAddress(library.getAddress());
        newLibrary.setWebsite(library.getWebsite());
        newLibrary.setYearOfConstruction(library.getYearOfConstruction());
        newLibrary.setTotalReaders(countReaders);
        newLibrary.setTotalBooks(countBooks);
        return newLibrary;
    }

    public static BookDTO_onlyLibraryID convertToBookDTO_forAll(Book book, ModelMapper modelMapper) {
        BookDTO_onlyLibraryID bookDTO = modelMapper.map(book, BookDTO_onlyLibraryID.class);
        bookDTO.setLibraryID(book.getLibrary().getID());
        return bookDTO;
    }

    public static ReaderDTO_forAll convertToReaderDTO_forAll(Reader reader, Long countReaders) {
        ReaderDTO_forAll newReader = new ReaderDTO_forAll();
        newReader.setID(reader.getID());
        newReader.setName(reader.getName());
        newReader.setEmail(reader.getEmail());
        newReader.setStudent(reader.isStudent());
        newReader.setGender(reader.getGender());
        newReader.setBirthDate(reader.getBirthDate());
        newReader.setTotalLibraries(countReaders);
        return newReader;
    }
}
