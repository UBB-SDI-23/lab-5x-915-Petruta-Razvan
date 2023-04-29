package com.example.restapi.model;

import com.example.restapi.model.membership.Membership;
import com.example.restapi.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libraries", indexes = {
        @Index(name = "name_libraries_index", columnList = "name")
})
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
    @Min(1800)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private User user;

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
