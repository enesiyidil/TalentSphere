package org.group3.rabbitMq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAuthModel {
    private Long authId;
    private String email;
    private String phone;
}
