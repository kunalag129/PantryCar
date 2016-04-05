package com.pantrycar.system.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by kunal.agarwal on 27/10/15.
 */
public class ApplicationHealthCheck extends HealthCheck {
    private final String template;

    public ApplicationHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return HealthCheck.Result.healthy();
    }
}

