package org.group3.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
public class PersonelSaveRequestDto {
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
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String phone;
    private String title;
    private String photo;
    private Double salary;

}
