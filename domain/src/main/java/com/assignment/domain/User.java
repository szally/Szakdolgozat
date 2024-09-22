package com.assignment.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String birthPlace;
    private Date birthDate;
    private String mothersName;
    private String idCardNumb;
    private Long taxNumb;
    private String email;
    private Credentials credentials;
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
