package com.github.sivaone.ai;

public class Application {

    static void main() {
        System.out.println("Hello User!");
        ClaudeChat chat = new ClaudeChat();
        chat.converseWithAi();
        System.exit(0);
    }
}
