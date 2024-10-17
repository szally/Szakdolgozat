package com.assignment.repository;

import com.assignment.domain.Iban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IbanRepository extends JpaRepository<Iban, Long> {
    Iban findByIban(String iban);
}
