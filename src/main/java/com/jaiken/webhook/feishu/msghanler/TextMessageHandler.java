package com.jaiken.webhook.feishu.msghanler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSONObject;
import com.jaiken.webhook.ai.OllamaService;
import com.jaiken.webhook.feishu.reply.ReplyMessageService;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TextMessageHandler extends ImService.P2MessageReceiveV1Handler {

	@Autowired
	private ReplyMessageService replyMessageService;

	@Autowired
	private OllamaService ollamaService;

	@Override
	public void handle(P2MessageReceiveV1 event) {
		String msgId = event.getEvent().getMessage().getMessageId();
		String messageStr = event.getEvent().getMessage().getContent();

		JSONObject messageJson = JSONObject.parseObject(messageStr);
		String msgContent = messageJson.get("text").toString();
		log.info("msgId: {}, message: {}", msgId, msgContent);
//		String reply = ollamaService.ollamaChat(msgContent);
//		String escapedReply = reply.replace("\\", "\\\\")
//				.replace("\"", "\\\"")
//				.replace("\n", "\\n")
//				.replace("\r", "\\r");
//		log.info("reply: {}", escapedReply);
//
//		replyMessageService.reply(msgId, escapedReply);

		replyMessageService.replyEmotion(msgId, "Get");
	}
}
