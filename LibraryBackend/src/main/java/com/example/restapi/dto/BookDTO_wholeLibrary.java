package com.example.restapi.dto;

import com.example.restapi.model.Library;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO_wholeLibrary extends BookDTO {
    private Library library;
}
