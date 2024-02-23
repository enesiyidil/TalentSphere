package org.group3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Communication{


    Long id;
    Company company;
    String name;
    String phoneNumber;
    EStatus status = EStatus.ACTIVE;
}
