package com.emc.documentum.sample;

import com.emc.documentum.springdata.repository.config.EnableDctmRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.emc.documentum.springdata, com.emc.documentum.sample")
@EnableAutoConfiguration
@EnableDctmRepositories
@EnableWebMvc
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
