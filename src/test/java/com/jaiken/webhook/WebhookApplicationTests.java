package com.jaiken.webhook;

import com.jaiken.webhook.ai.OllamaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebhookApplicationTests {

    @Autowired
    private OllamaService ollamaService;

    @Test
    void contextLoads() {

        ollamaService.ollamaChat("Hello");

        ollamaService.ollamaChat("How are you?");

        ollamaService.ollamaChat("What is your name?");

        ollamaService.ollamaChat("What is the weather like today?");

        ollamaService.ollamaChat("What is the capital of France?");

        ollamaService.ollamaChat("介绍下笛卡尔积");
    }

}
