package com.diplomaproject.tendersystembackend.service;

import com.diplomaproject.tendersystembackend.model.Statement;
import com.diplomaproject.tendersystembackend.model.Tender;

import java.util.List;

public interface StatementService {
    Statement createStatement(Long user_id, Long tender_id, Double totalCost);
    Statement findStatementById(Long id);
    List<Statement> getAllStatements();
    void deleteStatementById(Long id);
}
