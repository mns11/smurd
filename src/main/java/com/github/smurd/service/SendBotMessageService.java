package com.github.smurd.service;

/**
 * Service for sending messages via Telegram Bot.
 */
public interface SendBotMessageService {

    /**
     * Send message via Telegram Bot.
     *
     * @param chatId provided chatId in which messages would be sent.
     * @param message provided message to be sent.
     */
    void sendMessage(String chatId, String message);
}
