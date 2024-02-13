package org.group3.repository;

import org.group3.repository.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersonelRepository extends JpaRepository<Personel,Long> {
    List<Personel> findAllByCompanyId(Long companyId);
    List<Personel> findAllByManagerId(Long managerId);
    List<Personel> findByAuthId(Long authId);
}
