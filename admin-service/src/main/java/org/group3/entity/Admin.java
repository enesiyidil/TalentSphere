package org.group3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbladmin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long authId;

    private String name;

    private String surname;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String photo;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status=EStatus.ACTIVE;

    @Builder.Default
    private Long createdDate=System.currentTimeMillis();

    private Long updatedDate;
}
