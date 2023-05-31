package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByFirstname(String firstname);
    Optional<Users> findByEmail(String email);
}
