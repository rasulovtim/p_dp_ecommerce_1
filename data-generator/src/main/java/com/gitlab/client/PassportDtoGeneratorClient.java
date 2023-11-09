package com.gitlab.client;

import com.gitlab.controllers.api.rest.PassportRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "PassportDtoGenerator", url = "${app.feign.config.url}")
public interface PassportDtoGeneratorClient extends PassportRestApi {
}
