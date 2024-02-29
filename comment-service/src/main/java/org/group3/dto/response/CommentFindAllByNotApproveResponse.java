package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentFindAllByNotApproveResponse {
    Long id;
    String companyName;
    String personalName;
    String content;
    String title;

}
