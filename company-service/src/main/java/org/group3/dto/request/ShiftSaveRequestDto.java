package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftSaveRequestDto {

    String name;

    String  startTime;

    String  endTime;

    List<BreakSaveRequestDto> breaks;
}
