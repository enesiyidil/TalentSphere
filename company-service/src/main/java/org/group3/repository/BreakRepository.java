package org.group3.repository;

import org.group3.entity.Break;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BreakRepository extends JpaRepository<Break, Long> {
    List<Break> findAllByShiftId(Long shiftId);
}
