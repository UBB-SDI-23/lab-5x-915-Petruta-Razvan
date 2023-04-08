package com.example.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long ID;
    @Column
    @NotEmpty
    private String title;
    @Column
    @NotEmpty
    private String author;
    @Column
    @NotEmpty
    private String publisher;
    @Column
    @Min(0)
    private Double price;
    @Column
    @Min(1000)
    @Max(2023)
    private Integer publishedYear;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", nullable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Library library;
}
