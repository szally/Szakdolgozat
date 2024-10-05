package com.assignment.repository;

import com.assignment.domain.PartnerBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerBankRepository extends JpaRepository<PartnerBank, Long> {
    PartnerBank findBySwiftCode(String swift);
}
