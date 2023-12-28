package com.gitlab.clients;

import com.gitlab.controllers.api.rest.ProductImageRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "ProductImage", url = "${app.feign.config.url}")
public interface ProductImageClient extends ProductImageRestApi {
}