package com.example.currentlimitstarter.config;

import com.example.currentlimitstarter.aspect.AspectCurrent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrentLimitConfiguration {

    @Bean
    public AspectCurrent getAspectCurrent(){
        return new AspectCurrent();
    }

}
