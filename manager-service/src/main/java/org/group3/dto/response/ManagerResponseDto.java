package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerResponseDto {

    Long id;
    String name;
    String surname;
    String email;
    String phone;
    String photo;
    Long companyId;
    String title;
    List<Long> personals;
    LocalDateTime updatedDateTime;
    @Builder.Default
    LocalDateTime createdDateTime = LocalDateTime.now();

}
