package org.group3.manager;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "manager-service-manager", url = "http://localhost:9094/manager")
public interface IManagerServiceManager {
}
