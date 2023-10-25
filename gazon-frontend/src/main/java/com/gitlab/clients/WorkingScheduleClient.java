package com.gitlab.clients;

import com.gitlab.controllers.api.rest.WorkingScheduleRestApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "${app.feign.config.name}", contextId = "WorkingSchedule", url = "${app.feign.config.url}")
public interface WorkingScheduleClient extends WorkingScheduleRestApi {
}