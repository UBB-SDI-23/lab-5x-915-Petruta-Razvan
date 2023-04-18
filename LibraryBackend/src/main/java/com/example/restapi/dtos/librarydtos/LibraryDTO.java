package com.example.restapi.dtos.librarydtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class LibraryDTO {
    private Long ID;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @Email
    private String email;
    @NotEmpty
    private String website;
    @Min(1850)
    @Max(2023)
    private Integer yearOfConstruction;
}
