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
@Table(name = "IBAN")
public class Iban {
    @Id
    private String iban;

    @Column
    private String country;

    @OneToOne
    private Account account;
}
