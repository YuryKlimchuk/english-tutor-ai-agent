package com.hydroyura.eta;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModuleVerificationTest {

    @Test
    void verifyModularity() {
        ApplicationModules.of(EtaApplication.class).verify();
    }
}
