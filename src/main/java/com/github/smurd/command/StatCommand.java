package com.github.smurd.command;

import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.smurd.command.CommandUtils.getChatId;

/**
 * Statistics {@link Command}.
 */
public class StatCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String STAT_MESSAGE = "SMURD Telegram Bot is used by %s people.";

    @Autowired
    public StatCommand(SendBotMessageService sendBotMessageService,
            TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        sendBotMessageService.sendMessage(getChatId(update), String.format(STAT_MESSAGE,
                activeUserCount));
    }
}
