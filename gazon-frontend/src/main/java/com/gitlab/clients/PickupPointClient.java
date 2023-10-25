package com.gitlab.clients;

import com.gitlab.controllers.api.rest.PickupPointRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "PickupPoint", url = "${app.feign.config.url}")
public interface PickupPointClient extends PickupPointRestApi {
}
