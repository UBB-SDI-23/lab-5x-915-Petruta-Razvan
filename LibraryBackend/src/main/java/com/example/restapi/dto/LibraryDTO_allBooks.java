package com.example.restapi.dto;

import com.example.restapi.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO_allBooks extends LibraryDTO {
    private Set<Book> books;
    private Set<ReaderDTO_withMembership> readers;
}
