package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findDocumentByName(String fileName);
}
