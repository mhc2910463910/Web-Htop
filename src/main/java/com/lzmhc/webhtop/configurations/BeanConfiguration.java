package com.lzmhc.webhtop.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import oshi.SystemInfo;

@Configuration
public class BeanConfiguration {
    @Bean
    public SystemInfo systemInfo(){
        return new SystemInfo();
    }
}
