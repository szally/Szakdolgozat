package com.assignment.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;


@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String birthPlace;
    @Column
    private Date birthDate;
    @Column
    private String mothersName;
    @Column
    private String idCardNumb;
    @Column
    private Long taxNumb;
    @Column
    private String email;
    @Column(name = "points")
    private int points;
    @Embedded
    private Credentials credentials;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;
}
