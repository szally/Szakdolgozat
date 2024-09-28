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
    @Embedded
    private Credentials credentials;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;


    public User(Long id, String name, String birthPlace, Date birthDate, String mothersName, String idCardNumb, Long taxNumb, String email, Credentials credentials, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.mothersName = mothersName;
        this.idCardNumb = idCardNumb;
        this.taxNumb = taxNumb;
        this.email = email;
        this.credentials = credentials;
        this.status = status;
    }
}
