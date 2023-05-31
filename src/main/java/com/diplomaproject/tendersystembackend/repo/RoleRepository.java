package com.diplomaproject.tendersystembackend.repo;

import com.diplomaproject.tendersystembackend.model.Role;
import com.diplomaproject.tendersystembackend.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(ERole roleType);
}
