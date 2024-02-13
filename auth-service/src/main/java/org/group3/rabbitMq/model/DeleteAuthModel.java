package org.group3.rabbitMq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.Enums.EStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteAuthModel {
    private Long authid;
    private EStatus eStatus;
}
