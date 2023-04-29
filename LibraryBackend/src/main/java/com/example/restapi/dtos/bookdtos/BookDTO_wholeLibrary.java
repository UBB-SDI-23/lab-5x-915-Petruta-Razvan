package com.example.restapi.dtos.bookdtos;

import com.example.restapi.dtos.bookdtos.BookDTO;
import com.example.restapi.model.Library;
import com.example.restapi.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO_wholeLibrary extends BookDTO {
    private Library library;
    private String username;
}
