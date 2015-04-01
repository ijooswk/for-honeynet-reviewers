package com.fan2fan.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.fan2fan")
@Import({WebConfig.class, PersistentConfig.class})
@EnableAsync
@Configuration
public class TestingApplication {

}