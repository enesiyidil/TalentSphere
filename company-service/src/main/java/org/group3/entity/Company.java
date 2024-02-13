package org.group3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tbl_company")
public class Company implements IStatus{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long managerId;

    String name;

    @ElementCollection
    List<String> gallery;

    @ElementCollection
    List<String> payments;

    @ElementCollection
    List<Long> personals;

    @ElementCollection
    List<Long> communications;

    @ElementCollection
    List<Long> holidays;

    @ElementCollection
    List<Long> shifts;

    @Builder.Default
    LocalDateTime createdDateTime = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedDateTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.ACTIVE;
}
