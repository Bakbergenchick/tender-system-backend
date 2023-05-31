package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.Tender;
import com.diplomaproject.tendersystembackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenderRepository extends JpaRepository<Tender, Long> {
    Optional<Tender> findTenderByTenderName(String tenderName);

}
