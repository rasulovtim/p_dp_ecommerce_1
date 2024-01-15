package com.gitlab.clients;

import com.gitlab.controllers.api.rest.ProductRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "ProductDescription", url = "${app.feign.config.url}")
public interface ProductDescriptionClient extends ProductRestApi {
}
