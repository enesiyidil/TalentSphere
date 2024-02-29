package org.group3.repository;

import org.group3.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Boolean existsByEmail(String email);
    Optional<Auth> findOptionalByEmailAndPassword(String email, String password);
}
