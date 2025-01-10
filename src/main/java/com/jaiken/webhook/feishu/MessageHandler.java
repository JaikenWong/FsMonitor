package com.jaiken.webhook.feishu;

import com.jaiken.webhook.feishu.msghanler.TextMessageHandler;
import com.lark.oapi.event.EventDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MessageHandler {

    @Bean
    public EventDispatcher msgV2EventHandler(TextMessageHandler textMessageHandler) {
        return EventDispatcher.newBuilder("", "")
                .onP2MessageReceiveV1(textMessageHandler).build();
    }
}
