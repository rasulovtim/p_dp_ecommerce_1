package com.gitlab.clients;

import com.gitlab.controllers.api.rest.BankCardRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "BankCard", url = "${app.feign.config.url}")
public interface BankCardClient extends BankCardRestApi {
}
