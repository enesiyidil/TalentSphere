package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerUpdateRequestDto {


    String name;

    String surname;

    String email;

    String photo;

    String title;

    String phone;
}
