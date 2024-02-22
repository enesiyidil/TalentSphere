package org.group3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Personel {

    private Long id;

    private Long shiftId;
    private Long companyId;

    private Long authId;

    private  Long managerId;

    private String name;

    private String surname;

    private String email;

    private String phone;
    private String title;
    private String photo;
    private Double salary;
    List<Long> comment;
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private List<Long> annual_holiday;
}
