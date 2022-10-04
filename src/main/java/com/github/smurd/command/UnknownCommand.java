package com.github.smurd.command;

import com.github.smurd.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Unknown {@link Command}.
 */
public class UnknownCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String UNKNOWN_MESSAGE = "Не понимаю Вас \uD83D\uDE1F. Напишите /help, чтобы узнать, " +
            "что я понимаю.";

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), UNKNOWN_MESSAGE);
    }
}
