package org.group3.entity;

import jakarta.persistence.*;
import lombok.*;
import org.group3.entity.enums.EStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@ToString
@Table(name = "tbl_holiday")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long companyId;

    String name;

    String startDate;

    String endDate;

    String description;

    @ElementCollection
    List<Long> personals;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.PENDING;

    @Builder.Default
    String requestDate= LocalDateTime.now().toString();

    String approvalDate;
}
