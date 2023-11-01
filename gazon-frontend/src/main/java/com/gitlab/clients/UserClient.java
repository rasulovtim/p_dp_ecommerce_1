package com.gitlab.clients;


import com.gitlab.controller.api.UserRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "User", url = "${app.feign.config.url}")
public interface UserClient extends UserRestApi {
}
