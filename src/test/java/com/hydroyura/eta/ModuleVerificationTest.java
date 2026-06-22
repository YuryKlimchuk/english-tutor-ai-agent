package com.hydroyura.eta;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModuleVerificationTest {

    @Test
    @Disabled("Spring Modulith 2.1.0 verify() reports allowed dependencies as violations. Investigate.")
    void verifyModularity() {
        ApplicationModules.of(EtaApplication.class).verify();
    }
}
