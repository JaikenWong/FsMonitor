package com.jaiken.webhook.feishu.reply;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonParser;
import com.lark.oapi.Client;
import com.lark.oapi.core.utils.Jsons;
import com.lark.oapi.service.im.v1.model.ReplyMessageReq;
import com.lark.oapi.service.im.v1.model.ReplyMessageReqBody;
import com.lark.oapi.service.im.v1.model.ReplyMessageResp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public final class ReplyMessageService {

	@Autowired
	private Client client;

	public void reply(String msgId, String msg) {
		// 创建请求对象
		ReplyMessageReqBody body = ReplyMessageReqBody.newBuilder().content("{\"text\":\"" + msg + "\"}")
				.msgType("text").replyInThread(false).uuid(UUID.randomUUID().toString()).build();
		sendReply(msgId, body);
	}

	public void replyEmotion(String msgId, String msg) {
		// 创建请求对象
		ReplyMessageReqBody body = ReplyMessageReqBody.newBuilder()
				.content("{\"zh_cn\":{\"content\":[[{\"tag\":\"emotion\",\"emoji_type\":\""+msg+"\"}]]}}")
				.msgType("post")
				.replyInThread(false)
				.uuid(UUID.randomUUID().toString())
				.build();
		sendReply(msgId, body);
	}

	private void sendReply(String msgId, ReplyMessageReqBody body) {
		ReplyMessageReq req = ReplyMessageReq.newBuilder().messageId(msgId).replyMessageReqBody(body).build();
		// 发起请求
		try {
			ReplyMessageResp resp = client.im().message().reply(req);
			// 处理服务端错误
			if (!resp.success()) {
				log.error("code:{},msg:{},reqId:{}, resp:{}", resp.getCode(), resp.getMsg(),
						resp.getRequestId(), Jsons.createGSON(true, false).toJson(JsonParser.parseString(
								new String(resp.getRawResponse().getBody(), StandardCharsets.UTF_8))));
				return;
			}
			// 业务数据处理
			log.info(Jsons.DEFAULT.toJson(resp.getData()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
