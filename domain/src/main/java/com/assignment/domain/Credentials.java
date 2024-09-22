package com.assignment.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Credentials {
    private String username;
    private String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}