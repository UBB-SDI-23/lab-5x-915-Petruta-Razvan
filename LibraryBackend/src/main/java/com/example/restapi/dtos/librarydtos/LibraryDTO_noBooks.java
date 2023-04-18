package com.example.restapi.dtos.librarydtos;

import com.example.restapi.dtos.librarydtos.LibraryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO_noBooks extends LibraryDTO {
    private Long totalReaders;
    private Long totalBooks;
}
