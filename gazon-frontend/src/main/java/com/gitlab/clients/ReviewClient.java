package com.gitlab.clients;

import com.gitlab.controllers.api.rest.ReviewRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "Review", url = "${app.feign.config.url}")
public interface ReviewClient extends ReviewRestApi {
}