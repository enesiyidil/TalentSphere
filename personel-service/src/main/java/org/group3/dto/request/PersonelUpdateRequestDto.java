package org.group3.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PersonelUpdateRequestDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String title;
    private String photo;
    private Double salary;
}
