package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
