package org.group3.rabbitMq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveAuthModel {
    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String title;
}
