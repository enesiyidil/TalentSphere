package org.group3.manager;



import org.group3.dto.response.CommentFindAllByCompanyIdResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.group3.constant.EndPoints.*;


@FeignClient(name = "comment-manager", url = "${apiGatewayUrl}" + COMMENT )
public interface ICommentManager {

    @GetMapping (FIND_ALL_BY_COMPANY_ID)
    ResponseEntity<List<CommentFindAllByCompanyIdResponseDto>> findAllByCompanyId(@RequestParam Long companyId);
}
