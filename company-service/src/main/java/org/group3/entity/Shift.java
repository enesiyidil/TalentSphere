package org.group3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tbl_shift")
public class Shift implements IStatus{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne()
    Company company;

    String name;

    String startTime;

    String endTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shift")
    @Cascade(CascadeType.ALL)
    List<Break> breaks;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.ACTIVE;

}
