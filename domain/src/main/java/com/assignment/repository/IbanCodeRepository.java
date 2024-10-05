package com.assignment.repository;

import com.assignment.domain.IbanCodes;
import com.assignment.domain.PartnerBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IbanCodeRepository extends JpaRepository<IbanCodes, Long> {
    IbanCodes findByIban(String iban);
}
