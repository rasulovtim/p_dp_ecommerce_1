package com.gitlab.clients;

import com.gitlab.controllers.api.rest.ReviewImageRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "ReviewImage", url = "${app.feign.config.url}")
public interface ReviewImageClient extends ReviewImageRestApi {
}