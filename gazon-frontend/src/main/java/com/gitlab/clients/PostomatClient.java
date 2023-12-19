package com.gitlab.clients;

import com.gitlab.controllers.api.rest.PostomatRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "Postomat", url = "${app.feign.config.url}")

public interface PostomatClient extends PostomatRestApi {
}
