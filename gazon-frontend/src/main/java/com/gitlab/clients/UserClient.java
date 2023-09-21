package com.gitlab.clients;

import com.gitlab.controllers.api.rest.UserRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "User", url = "${app.feign.config.url}")
public interface UserClient extends UserRestApi {
}
