package org.group3.repository;

import org.group3.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);
}
