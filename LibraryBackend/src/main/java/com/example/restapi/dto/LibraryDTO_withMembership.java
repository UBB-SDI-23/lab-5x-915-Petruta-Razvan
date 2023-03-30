package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryDTO_withMembership extends LibraryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
