package com.progfilthi.filthiblog;

import com.progfilthi.filthiblog.config.AdminProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AdminProperties.class)
public class FilthiBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilthiBlogApplication.class, args);
    }

}
