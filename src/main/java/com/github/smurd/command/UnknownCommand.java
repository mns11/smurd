package com.github.smurd.command;

import com.github.smurd.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.smurd.command.CommandUtils.getChatId;

/**
 * Unknown {@link Command}.
 */
public class UnknownCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String UNKNOWN_MESSAGE =
            "I don't understand you \uD83D\uDE1F. Type /help to see what I understand.";

    public UnknownCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), UNKNOWN_MESSAGE);
    }
}
