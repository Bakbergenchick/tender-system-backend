package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<Statement, Long> {
}
