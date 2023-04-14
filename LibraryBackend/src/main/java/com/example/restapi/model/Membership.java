package com.example.restapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "memberships", indexes = {
        @Index(name = "fk_library_id_memberships_index", columnList = "library_id"),
        @Index(name = "fk_reader_id_memberships_index", columnList = "reader_id")
})
public class Membership {
    @EmbeddedId
    private MembershipKey ID;
    @ManyToOne
    @MapsId("libraryID")
    @JsonIgnore
    @JoinColumn(name = "library_id")
    private Library library;
    @ManyToOne
    @MapsId("readerID")
    @JsonIgnore
    @JoinColumn(name = "reader_id")
    private Reader reader;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
}
