package com.infoorigin.loanApprovalBackend;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    private static final Logger logger = LoggerFactory.getLogger(DroolsConfig.class);
    private static final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer kieContainer() {
        logger.info("Initializing Drools KIE container...");

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/eligibility_rules.drl"));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        if (kieBuilder.getResults().hasMessages(org.kie.api.builder.Message.Level.ERROR)) {
            String errors = kieBuilder.getResults().toString();
            logger.error("Errors found in Drools rules: {}", errors);
            throw new IllegalStateException("Failed to build Drools rules: " + errors);
        }

        KieModule kieModule = kieBuilder.getKieModule();
        logger.info("Drools KIE container initialized successfully.");
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
