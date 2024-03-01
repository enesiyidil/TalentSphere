package org.group3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "tbl_break")
public class Break implements IStatus{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne()
    Shift shift;

    String name;

    String startTime;

    String endTime;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.ACTIVE;

}
