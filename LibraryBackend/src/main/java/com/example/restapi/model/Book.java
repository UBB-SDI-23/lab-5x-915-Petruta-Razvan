package com.example.restapi.model;

import com.example.restapi.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books", indexes = {
        @Index(name = "fk_library_id_books_index", columnList = "library_id"),
        @Index(name = "price_books_index", columnList = "price")
})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long ID;

    @Column
    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @Column
    @NotEmpty
    private String publisher;

    @Min(0)
    private Double price;

    @Min(1000)
    @Max(2023)
    private Integer publishedYear;

    @Column(length = 2000)
    @NotEmpty
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", nullable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Library library;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private User user;
}
