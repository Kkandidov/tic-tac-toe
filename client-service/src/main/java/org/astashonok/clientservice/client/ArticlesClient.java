package org.astashonok.clientservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "articles-service", url = "http://127.0.0.1:8090")
public interface ArticlesClient {

    @GetMapping("/articles")
    String[] getAllArticles();
}
