package com.jaiken.webhook.feishu;


import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.ws.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ClientHandler {
    @Value("${feishu.app-id}")
    private String appId;

    @Value("${feishu.app-secret}")
    private String appSecret;


    @Bean
    public Client wsClient(EventDispatcher msgV2EventHandler) {
        Client cli = new Client.Builder(appId, appSecret)
                .eventHandler(msgV2EventHandler)
                .build();
        cli.start();
        return cli;
    }

    @Bean
    public com.lark.oapi.Client client() {
        return new com.lark.oapi.Client.Builder(appId, appSecret)
                .build();
    }
}
