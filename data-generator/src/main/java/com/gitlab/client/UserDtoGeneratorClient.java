package com.gitlab.client;

import com.gitlab.controllers.api.rest.UserRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "UserDtoGenerator", url = "${app.feign.config.url}")
public interface UserDtoGeneratorClient extends UserRestApi {
}
