package org.group3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    String address;

    @ElementCollection
    List<String> gallery;

    @ElementCollection
    @Fetch(FetchMode.JOIN)
    List<String> payments;

    @ElementCollection
    List<Long> personals;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    List<Communication> communications;

    @ElementCollection
    List<Long> holidays;

    @OneToMany(mappedBy = "company")
    List<Shift> shifts;

    @Builder.Default
    LocalDateTime createdDateTime = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedDateTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.ACTIVE;


}
