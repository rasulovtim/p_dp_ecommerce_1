package com.gitlab.clients;

import com.gitlab.controllers.api.rest.ExampleRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "Example", url = "${app.feign.config.url}")
public interface ExampleClient extends ExampleRestApi {
}