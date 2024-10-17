package com.assignment.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "iban")
public class Iban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String iban;

    @Column
    private String country;

    @OneToMany(mappedBy = "ibanCodes")
    private List<Account> accounts;
}
