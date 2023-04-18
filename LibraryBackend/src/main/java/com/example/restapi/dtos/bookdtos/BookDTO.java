package com.example.restapi.dtos.bookdtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BookDTO {
    private Long ID;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String publisher;
    @Min(0)
    private Double price;
    @Min(1000)
    @Max(2023)
    private Integer publishedYear;
    @NotEmpty
    private String description;
}
