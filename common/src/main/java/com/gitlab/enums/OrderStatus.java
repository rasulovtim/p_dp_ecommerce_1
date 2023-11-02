package com.gitlab.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

        NOT_PAID,
        PAID,
        IN_PROGRESS,
        ARRIVED,
        DONE
}