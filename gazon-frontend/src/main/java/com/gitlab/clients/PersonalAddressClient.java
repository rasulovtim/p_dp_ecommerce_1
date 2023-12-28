package com.gitlab.clients;

import com.gitlab.controllers.api.rest.PersonalAddressRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "PersonalAddress", url = "${app.feign.config.url}")
public interface PersonalAddressClient extends PersonalAddressRestApi {
}
