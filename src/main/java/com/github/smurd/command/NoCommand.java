package com.github.smurd.command;

import com.github.smurd.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.smurd.command.CommandUtils.getChatId;

/**
 * No {@link Command}.
 */
public class NoCommand implements Command{

    private final SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "I support commands that start with a slash (/).\n"
            + "To see a list of commands, type /help.";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), NO_MESSAGE);
    }
}
