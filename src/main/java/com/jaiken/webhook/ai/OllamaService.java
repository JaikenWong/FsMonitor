package com.jaiken.webhook.ai;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class OllamaService {

	@Autowired
	@Qualifier("ollamaChatClient")
	private OllamaChatClient ollamaChatClient;

	public String ollamaChat(String msg) {
		Prompt prompt = new Prompt(msg);
		ChatResponse chatResponse = ollamaChatClient.call(prompt);
		log.info("chatResponse: {}", chatResponse);
		return chatResponse.getResult().getOutput().getContent();
	}


	public Flux<String> ollamaChat4Flow(String msg) {
		Prompt prompt = new Prompt(msg);
		Flux<ChatResponse> res=  ollamaChatClient.stream(prompt);
		return res.map(chatResponse -> {
			// Extract the content from each ChatResponse
			String content = chatResponse.getResult().getOutput().getContent();
			log.info("chatResponse content: {}", content);
			return content;
		}).doOnError(error -> {
			// Handle any errors here
			log.error("Error occurred while streaming chat responses", error);
		}).doOnComplete(() -> {
			// Handle completion here
			log.info("Streaming chat responses completed");
		});
	}
}
