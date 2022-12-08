package com.github.smurd.command;

import static com.github.smurd.command.CommandName.DELETE_DRUMMER_SUB;
import static com.github.smurd.command.CommandUtils.getChatId;
import static com.github.smurd.command.CommandUtils.getMessage;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.repository.entity.TelegramUser;
import com.github.smurd.service.DrummerSubService;
import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ws.rs.NotFoundException;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Delete Drummer subscription {@link Command}.
 */
public class DeleteDrummerSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final DrummerSubService drummerSubService;

    public DeleteDrummerSubCommand(SendBotMessageService sendBotMessageService,
            DrummerSubService drummerSubService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.drummerSubService = drummerSubService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(DELETE_DRUMMER_SUB.getCommandName())) {
            sendDrummerIdList(getChatId(update));
            return;
        }
        String drummerId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(drummerId)) {
            Optional<DrummerSub> optionalDrummerSub =
                    drummerSubService.findById(Integer.valueOf(drummerId));
            if (optionalDrummerSub.isPresent()) {
                DrummerSub drummerSub = optionalDrummerSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId)
                        .orElseThrow(NotFoundException::new);
                drummerSub.getUsers().remove(telegramUser);
                drummerSubService.save(drummerSub);
                sendBotMessageService.sendMessage(chatId,
                        format("Unsubscribed from the drummer: %s", drummerSub.getName()));
            } else {
                sendBotMessageService.sendMessage(chatId,
                        "Drummer not found in subscriptions =/");
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "Wrong drummer ID format.\n " +
                    "ID must be a positive integer.");
        }
    }

    private void sendDrummerIdList(Long chatId) {
        String message;
        List<DrummerSub> drummerSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getDrummerSubs();
        if (CollectionUtils.isEmpty(drummerSubs)) {
            message = "There are no drummer subscriptions yet."
                    + " To add a subscription write /addGroupSub";
        } else {
            String userDrummerSubData = drummerSubs.stream()
                    .map(drummer -> format("%s - %s %n", drummer.getName(), drummer.getId()))
                    .collect(Collectors.joining());

            message = format("""
                    To remove a subscription to a drummer - \s
                    send the command line along with the drummer ID.\s
                    For example: /deleteDrummerSub 11\s

                    List of all drummers you are subscribed to\s

                    drummer name - drummer ID\s

                    %s""", userDrummerSubData);
        }

        sendBotMessageService.sendMessage(chatId, message);
    }
}
