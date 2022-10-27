package com.github.smurd.command;

import com.github.smurd.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.smurd.command.CommandName.*;
import static com.github.smurd.command.CommandUtils.getChatId;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("""
                    ✨<b>Available commands</b>✨

                    <b>Start / end work with the bot</b>
                    %s - get started
                    %s - stop working

                    %s - get help
                    %s - get statistics
                    """,
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), STAT.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(getChatId(update), HELP_MESSAGE);
    }
}
