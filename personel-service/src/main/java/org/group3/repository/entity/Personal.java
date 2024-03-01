package org.group3.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.repository.entity.enums.EGender;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "tbl_personal")
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shiftId;

    private Long companyId;

    @Column(unique = true)
    private Long authId;

    private  Long managerId;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String surname;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String phone;

    private String title;

    private String photo;

    private Double salary;

    @ElementCollection
    private List<String> payments;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EGender gender = EGender.NO_GENDER;

    @ElementCollection
    List<Long> comment;

    @Builder.Default
    private String createdDate=LocalDateTime.now().toString();

    private String updatedDate;
    @ElementCollection
    private List<Long> annual_holiday;
}
