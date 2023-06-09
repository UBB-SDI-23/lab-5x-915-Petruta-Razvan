package com.example.restapi.model;

import com.example.restapi.model.membership.Membership;
import com.example.restapi.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "readers")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long ID;

    @Column
    @NotEmpty
    private String name;

    @NotBlank(message = "it should not be empty")
    @Email
    private String email;

    @Column
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column
    @NotEmpty
    @Pattern(regexp = "^(male|female)$", message = "Gender should be 'male' or 'female'")
    private String gender;

    @Column
    private boolean isStudent;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Membership> memberships;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private User user;

    public void addMembership(Membership membership) {
        this.memberships.add(membership);
    }
}
