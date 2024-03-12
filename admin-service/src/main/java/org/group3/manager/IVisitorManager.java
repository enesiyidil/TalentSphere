package org.group3.manager;

import org.group3.dto.response.VisitorFindAllResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.group3.constant.EndPoints.FIND_ALL;
import static org.group3.constant.EndPoints.VISITOR;

@FeignClient(name = "visitor-manager", url = "http://${visitor-service}:9096" + VISITOR )
//@FeignClient(name = "visitor-manager", url = "${apiGatewayUrl}" + VISITOR )
public interface IVisitorManager {

    @GetMapping(FIND_ALL)
    ResponseEntity<List<VisitorFindAllResponseDto>> findAll();
}
