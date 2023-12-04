package com.gitlab.client;

import com.gitlab.controllers.api.rest.UserRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "UserGenerator", url = "${app.feign.config.url}")
public interface UserGeneratorClient extends UserRestApi {
}