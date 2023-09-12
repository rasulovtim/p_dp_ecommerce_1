package com.gitlab.clients;

import com.gitlab.controllers.api.rest.RoleRestApi;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "${app.feign.config.name}", contextId = "Role", url = "${app.feign.config.url}")
public interface RoleClient extends RoleRestApi {
}
