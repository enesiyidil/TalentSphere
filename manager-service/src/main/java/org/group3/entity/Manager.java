package org.group3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EGender;
import org.group3.entity.enums.EPackage;
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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EGender gender = EGender.NO_GENDER;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EPackage ePackage = EPackage.P_30;

    @Column(unique = true)
    Long companyId;

    String title;

    @ElementCollection
    List<Long> personals;

    @Builder.Default
    String createdDateTime = LocalDateTime.now().toString();

    String updatedDateTime;

    String phone;
}
