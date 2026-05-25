# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Build
./gradlew build          # or gradlew.bat on Windows

# Run tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.github.sivaone.ai.SomeTest"
```

## Environment Setup

The app reads `ANTHROPIC_API_KEY` from the environment via `AnthropicOkHttpClient.fromEnv()`. A `.env` file at the project root provides this key locally.

## Architecture

A minimal Java CLI project using the [Anthropic Java SDK](https://github.com/anthropics/anthropic-sdk-java) (`com.anthropic:anthropic-java:2.34.0`).

- **`Application.java`** — entry point; instantiates `ClaudeChat` and starts the conversation loop.
- **`ClaudeChat.java`** — maintains an in-memory `List<MessageParam>` conversation history, sends it to `claude-sonnet-4-6` via `AnthropicOkHttpClient`, and prints responses to stdout. The loop reads from stdin and exits on `"exit"` or empty input.

The client is instantiated once per `ClaudeChat` instance. All turns (user + assistant) are appended to `messages` so multi-turn context is preserved within a single session.

## Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| `com.anthropic:anthropic-java` | 2.34.0 | Anthropic SDK (OkHttp transport) |
| `org.junit:junit-bom` | 6.0.3 | JUnit 5 BOM |
| `org.junit.jupiter:junit-jupiter` | (from BOM) | JUnit 5 tests |
