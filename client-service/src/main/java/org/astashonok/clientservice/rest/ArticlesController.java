package org.astashonok.clientservice.rest;

import org.astashonok.clientservice.client.ArticlesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticlesController {

    @Autowired
    private ArticlesClient articlesClient;

    @GetMapping(value = "/articles")
    public String[] getArticles() {
        return articlesClient.getAllArticles();
    }
}
