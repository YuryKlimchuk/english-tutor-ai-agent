package com.hydroyura.eta;

import com.structurizr.Workspace;
import com.structurizr.model.Model;
import com.structurizr.model.SoftwareSystem;
import com.structurizr.view.ViewSet;
import com.structurizr.view.ComponentView;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModuleDocumentationTest {

    @Test
    void generateComponentDiagram() throws Exception {
        var modules = ApplicationModules.of(EtaApplication.class);

        new org.springframework.modulith.docs.Documenter(modules)
            .writeDocumentation();

        System.out.println("Documentation generated in target/spring-modulith-docs/");
    }
}
