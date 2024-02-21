package org.group3.repository;

import org.group3.entity.Comment;
import org.group3.entity.enums.EStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByStatusEquals(EStatus status);
}
