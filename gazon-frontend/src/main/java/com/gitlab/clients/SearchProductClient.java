package com.gitlab.clients;

import com.gitlab.controllers.api.rest.SearchProductRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "SearchProduct", url = "${app.feign.config.url}")
public interface SearchProductClient extends SearchProductRestApi {
}