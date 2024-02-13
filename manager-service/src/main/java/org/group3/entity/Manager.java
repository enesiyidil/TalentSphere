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
@Table(name = "tbl_manager")
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    Long authId;

    String name;

    String surname;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.ACTIVE;

    @Column(unique = true)
    String email;

    String photo;

    @ElementCollection
    List<Long> companies;

    String title;

    @ElementCollection
    List<Long> personals;

    @Builder.Default
    LocalDateTime createdDateTime = LocalDateTime.now();

    @Builder.Default
    LocalDateTime updatedDateTime = LocalDateTime.now();

    String phone;
}
