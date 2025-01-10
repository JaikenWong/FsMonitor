package com.jaiken.webhook;

import com.google.gson.JsonParser;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.im.v1.model.ReplyMessageReq;
import com.lark.oapi.service.im.v1.model.ReplyMessageReqBody;
import com.lark.oapi.service.im.v1.model.ReplyMessageResp;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public final class ReplyMessageSample {

    public static void reply(String msgId, String msg) {
        // 构建client
        Client client = Client.newBuilder(Config.APP_ID, Config.APP_SECRET).build();

        // 创建请求对象
        ReplyMessageReq req = ReplyMessageReq.newBuilder()
                .messageId(msgId)
                .replyMessageReqBody(ReplyMessageReqBody.newBuilder()
                        .content("{\"text\":\"" + msg + "\"}")
                        .msgType("text")
                        .replyInThread(true)
                        .uuid(UUID.randomUUID().toString())
                        .build())
                .build();

        // 发起请求
        try {
            ReplyMessageResp resp = client.im().message().reply(req);
            // 处理服务端错误
            if (!resp.success()) {
                log.error("code:{},msg:{},reqId:{}, resp:{}", resp.getCode(), resp.getMsg(), resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8))));
                return;
            }
            // 业务数据处理
            log.info(Jsons.DEFAULT.toJson(resp.getData()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
