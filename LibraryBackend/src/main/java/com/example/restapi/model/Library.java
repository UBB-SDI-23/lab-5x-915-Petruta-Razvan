package com.example.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libraries")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long ID;
    @Column
    @NotEmpty
    private String name;
    @Column
    @NotEmpty
    private String address;
    @Column
    @Email
    private String email;
    @Column
    @NotEmpty
    private String website;
    @Column
    @Min(1850)
    @Max(2023)
    private Integer yearOfConstruction;
    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Book> books;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Membership> memberships;

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public void addMembership(Membership membership) {
        this.memberships.add(membership);
    }
}
