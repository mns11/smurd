package com.github.smurd.command;

import com.github.smurd.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String START_MESSAGE = "Привет! Я SMURD Telegram Bot. Я помогу Вам быть в курсе последних " +
            "событий. Я еще маленький и только учусь.";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}
