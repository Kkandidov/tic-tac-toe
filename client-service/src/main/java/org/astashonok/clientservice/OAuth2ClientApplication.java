package org.astashonok.clientservice;

import org.astashonok.clientservice.rest.ArticlesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OAuth2ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ClientApplication.class, args);
    }

    @Autowired
    private ArticlesController articlesController;

    @Bean
    public String b(){
        String[] articles = articlesController.getArticles();

        System.out.println("wererer");
        return null;
    }

}
