package com.jaiken.webhook;

import com.alibaba.fastjson2.JSONObject;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import com.lark.oapi.ws.Client;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Simple {

    public static void main(String[] args) {
        EventDispatcher eventHandler = createEventHandler();
        Client cli = new Client.Builder(Config.APP_ID, Config.APP_SECRET)
                .eventHandler(eventHandler)
                .build();
        cli.start();
    }

    private static EventDispatcher createEventHandler() {
        return EventDispatcher.newBuilder("", "")
                .onP2MessageReceiveV1(new ImService.P2MessageReceiveV1Handler() {
                    @Override
                    public void handle(P2MessageReceiveV1 event) {
                        String msgId = event.getEvent().getMessage().getMessageId();
                        String messageStr = event.getEvent().getMessage().getContent();

                        JSONObject messageJson = JSONObject.parseObject(messageStr);
                        log.info("msgId: {}, message: {}", msgId, messageJson.get("text"));
                        
                        ReplyMessageSample.reply(msgId, "Hello, I'm a bot.");
                    }
                }).build();
    }
}
