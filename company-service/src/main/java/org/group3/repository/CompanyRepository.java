package org.group3.repository;

import org.group3.dto.response.CompanyResponseDto;
import org.group3.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByManagerId(Long managerId);

    List<Company> findByPersonalsContains(Long personalId);

    List<Company> findAllByManagerIdIsNull();

    Company findByName(String name);


}
