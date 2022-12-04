package com.github.smurd.command;

import com.github.smurd.repository.entity.TelegramUser;
import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.smurd.command.CommandUtils.getChatId;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public static final String START_MESSAGE =
            "Hello! I am SMURD Telegram Bot. I will help you keep up to date with " +
            "drummers. I'm still small and just learning.";

    public StartCommand(SendBotMessageService sendBotMessageService,
            TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = getChatId(update);

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatId);
                    telegramUserService.save(telegramUser);
                });

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
