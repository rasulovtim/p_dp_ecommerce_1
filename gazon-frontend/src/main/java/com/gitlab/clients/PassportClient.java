package com.gitlab.clients;

import com.gitlab.controllers.api.rest.PassportRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "Passport", url = "${app.feign.config.url}")
public interface PassportClient extends PassportRestApi {
}
