package org.group3.repository;

import org.group3.repository.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPersonelRepository extends JpaRepository<Personel,Long> {
    List<Personel> findAllByCompanyId(Long companyId);
    List<Personel> findAllByManagerId(Long managerId);
    Optional<Personel> findByAuthId(Long authId);
}
