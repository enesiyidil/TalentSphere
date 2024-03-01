package org.group3.manager;

import org.group3.dto.request.AdminSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.group3.constant.EndPoints.*;

@FeignClient(name = "auth-manager", url = "${apiGatewayUrl}" + AUTH )
public interface IAuthManager {
    @PostMapping(ADMIN_SAVE)
    ResponseEntity<Long> adminSave(@RequestBody AdminSaveRequestDto dto);
}
