package org.group3.dto.response;

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

public class Break {


    Long id;


    Shift shift;

    String name;

    String startTime;

    String endTime;


    EStatus status ;
}
