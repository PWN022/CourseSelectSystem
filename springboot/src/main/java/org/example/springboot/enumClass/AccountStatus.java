package org.example.springboot.enumClass;

import io.swagger.v3.oas.annotations.media.Schema;

public enum AccountStatus {
    @Schema(description = "待审核") PENDING_REVIEW(0),
    @Schema(description = "审核通过") REVIEW_SUCCESS(1),
    @Schema(description = "审核不通过") REVIEW_FAILED(2),
    @Schema(description = "正常状态") NORMAL(1),
    @Schema(description = "禁用状态") DISABLED(0);

    private final Integer value;

    AccountStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}