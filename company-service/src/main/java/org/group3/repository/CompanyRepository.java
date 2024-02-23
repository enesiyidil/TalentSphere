package org.group3.repository;

import org.group3.dto.response.CompanyResponseDto;
import org.group3.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByManagerId(Long managerId);

    List<Company> findByPersonalsContains(Long personalId);

    List<Company> findAllByManagerIdIsNull();

    Company findByName(String name);




}
