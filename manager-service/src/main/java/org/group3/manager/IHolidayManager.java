package org.group3.manager;

import org.group3.dto.response.HolidayResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.group3.constant.EndPoints.*;

@FeignClient(name = "holiday-manager", url = "http://${holiday-service}:9101" + HOLIDAY )
//@FeignClient(name = "holiday-manager", url = "${apiGatewayUrl}" + HOLIDAY )
public interface IHolidayManager {

    @GetMapping(FIND_ALL_BY_COMPANY_ID)
    ResponseEntity<List<HolidayResponseDto>> findAllByCompanyId(@RequestParam Long companyId);

}
