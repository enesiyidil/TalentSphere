package org.group3.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "tbl_personel")
public class Personel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private Long shiftId;
    private Long companyId;
    @Column(unique = true)
    private Long authId;
    @Column(unique = true)
    private  Long managerId;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String surname;
    @Column(length = 50, unique = true)
    private String email;
    @Column(length = 50)
    private String phone;
    private String title;
    private String photo;
    private Double salary;
    @ElementCollection
    List<Long> comment;
    @Builder.Default
    private LocalDateTime createdDate=LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedDate=LocalDateTime.now();
    @ElementCollection
    private List<Long> annual_holiday;
}
