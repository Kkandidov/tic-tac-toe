package org.astashonok.clientservice.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class ArticlesControllerTest {

    @Autowired
    private ArticlesController articlesController;

    @Test
    public void test() {
        String[] articles = articlesController.getArticles();

        System.out.println("wererer");
    }

}