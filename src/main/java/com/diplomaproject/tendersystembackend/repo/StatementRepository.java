package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.Statement;
import com.diplomaproject.tendersystembackend.model.Tender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatementRepository extends JpaRepository<Statement, Long> {
    Optional<Statement> findStatementById(Long id);
}
