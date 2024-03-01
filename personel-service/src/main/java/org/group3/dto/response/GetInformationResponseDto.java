package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.dto.request.Company;
import org.group3.dto.request.ShiftGetInformationResponseDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetInformationResponseDto {
    String managerName;
    CompanyInformationResponseDto company;
    ShiftGetInformationResponseDto shift;
    List<CommentFindAllByCompanyIdWithPersonalNameResponseDto> comments;


}
