package org.group3.manager;

import org.group3.dto.response.CompanyFindByNameResponseDto;
import org.group3.dto.response.GetInformationForVisitorResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@FeignClient(name = "company-manager", url = "${apiGatewayUrl}" + COMPANY )
public interface ICompanyManager {

    @GetMapping(FIND_BY_NAME)
    ResponseEntity<CompanyFindByNameResponseDto> findByName(@RequestParam String name);

    @GetMapping(GET_INFORMATION_FOR_VISITOR)
    ResponseEntity<List<GetInformationForVisitorResponseDto>> getInformationForVisitor();


}
