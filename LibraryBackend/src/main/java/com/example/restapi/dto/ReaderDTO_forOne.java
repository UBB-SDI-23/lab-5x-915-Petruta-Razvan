package com.example.restapi.dto;

import com.example.restapi.model.Library;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO_forOne extends ReaderDTO {
    private Set<LibraryDTO_withMembership> libraries;
}
