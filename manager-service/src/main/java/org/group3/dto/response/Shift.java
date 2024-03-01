package org.group3.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Shift{


    Long id;


    Company company;

    String name;

    String startTime;

    String endTime;


    List<Break> breaks;


    EStatus status ;

}
