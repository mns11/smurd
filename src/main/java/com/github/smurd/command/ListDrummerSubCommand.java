package com.github.smurd.command;

import static com.github.smurd.command.CommandUtils.getChatId;

import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.repository.entity.TelegramUser;
import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * {@link Command} for getting list of {@link DrummerSub}.
 */
public class ListDrummerSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListDrummerSubCommand(SendBotMessageService sendBotMessageService,
            TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        //todo add exception handling
        TelegramUser telegramUser = telegramUserService.findByChatId(getChatId(update))
                .orElseThrow(NotFoundException::new);

        String message = "List of all your drummer subscriptions: \n\n";
        String collectedDrummers = telegramUser.getDrummerSubs().stream()
                .map(it -> "Drummer: " + it.getName() + ", ID = " + it.getId() + " \n")
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(telegramUser.getChatId(),
                message + collectedDrummers);
    }
}
