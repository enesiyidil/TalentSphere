package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.Enums.EStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDto {
    private Long id;
    private String username;
    private String email;
    private EStatus status;
}
