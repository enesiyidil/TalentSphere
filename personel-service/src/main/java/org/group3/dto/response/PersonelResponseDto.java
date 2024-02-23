package org.group3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class PersonelResponseDto {

    private Long id;
    private Long companyId;
    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String title;
    private String photo;
    private Double salary;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;




}
