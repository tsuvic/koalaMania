package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class TwitterConfig {
	@Autowired
    Environment mEnv;
    
    @Bean(name="spring.social.twitter.app-id")
    public String getTwitterAppId()
    {
        return mEnv.getProperty("spring.social.twitter.app-id");
    }
    
    @Bean(name="spring.social.twitter.app-secret")
    public String getTwitterAppSecret()
    {
        return mEnv.getProperty("spring.social.twitter.app-secret");
    }
    
    @Bean(name="spring.social.twitter.callback-url")
    public String getCallBackUrl()
    {
        return mEnv.getProperty("spring.social.twitter.callback-url");
    }
    
}
