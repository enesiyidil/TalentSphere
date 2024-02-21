package org.group3.repository;

import org.group3.entity.Holiday;
import org.group3.entity.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findAllByCompanyId(Long companyId);

    List<Holiday> findAllByCompanyIdAndStatus (Long companyId, EStatus status);

}
