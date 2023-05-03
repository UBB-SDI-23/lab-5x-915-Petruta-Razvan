package com.example.restapi.model.user;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Size(max = 150)
    private String bio;

    @Column
    @Size(max = 100)
    private String location;

    @Column
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column
    @Pattern(regexp = "^(male|female)$", message = "Gender should be 'male' or 'female'")
    private String gender;

    @Column
    private String maritalStatus;
}
