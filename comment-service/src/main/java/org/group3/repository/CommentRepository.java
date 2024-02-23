package org.group3.repository;

import org.group3.dto.response.CommentFindAllByPersonalIdResponseDto;
import org.group3.entity.Comment;
import org.group3.entity.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByStatusEquals(EStatus status);

    List<Comment> findAllByPersonalId(Long personalId);

    List<Comment> findAllByCompanyId(Long companyId);
}
