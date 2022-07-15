package org.astashonok.oauthclientstarter.configurations;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("org.astashonok.oauthclientstarter")
@Configuration
@EnableFeignClients("org.astashonok")
public class OAuthClientAutoConfiguration {

}
