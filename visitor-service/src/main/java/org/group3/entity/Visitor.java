package org.group3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tblvisitor")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authId;
    private String name;
    private String surname;
    @Column(unique = true)
    private String email;
    private String phone;
    private String photo;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status=EStatus.ACTIVE;

    @Builder.Default
    private Long createdDate=System.currentTimeMillis();
    private Long updatedDate;
}
