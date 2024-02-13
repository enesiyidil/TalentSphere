package org.group3.repository;

import org.group3.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);
}
