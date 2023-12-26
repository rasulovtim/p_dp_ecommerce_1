package com.gitlab.clients;

import com.gitlab.controllers.api.rest.ProductSearchRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "SearchProduct", url = "${app.feign.config.url}")
public interface ProductSearchClient extends ProductSearchRestApi {
}