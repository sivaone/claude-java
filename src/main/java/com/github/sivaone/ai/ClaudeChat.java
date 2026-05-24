package com.github.sivaone.ai;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClaudeChat {

    private final List<MessageParam> messages = new ArrayList<>();
    private final AnthropicClient client = AnthropicOkHttpClient.fromEnv();

    private void addUserMessage(String text) {
        var userMessage = MessageParam.builder()
                .role(MessageParam.Role.USER)
                .content(text)
                .build();
        messages.add(userMessage);
    }

    private void addAssistantMessage(String text) {
        var assistantMessage = MessageParam.builder()
                .role(MessageParam.Role.ASSISTANT)
                .content(text)
                .build();
        messages.add(assistantMessage);
    }

    private String chat() {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_SONNET_4_6)
                .maxTokens(1024)
                .messages(messages)
                .build();
        Message response = client.messages().create(params);
        // System.out.println(response);
        Optional<TextBlock> textBlock = response.content().getFirst().text();
        return textBlock.map(TextBlock::text).orElse("No response from AI");
    }

    public void converseWithAi() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            var text = scanner.hasNextLine() ? scanner.nextLine() : "exit";
            if ("exit".equalsIgnoreCase(text) || "".equals(text)) {
                break;
            }
            addUserMessage(text);
            var response = chat();
            addAssistantMessage(response);
            System.out.printf("---%n%s%n---%n", response);
        }
        System.out.println("Goodbye!");
        scanner.close();
    }
}
