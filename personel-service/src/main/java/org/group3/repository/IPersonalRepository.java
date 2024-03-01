package org.group3.repository;

import org.group3.repository.entity.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPersonalRepository extends JpaRepository<Personal,Long> {
    List<Personal> findAllByCompanyId(Long companyId);
    List<Personal> findAllByManagerId(Long managerId);
    Optional<Personal> findByAuthId(Long authId);
}
