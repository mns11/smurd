package com.github.smurd.command;

import com.github.smurd.repository.DrummerRepository;
import com.github.smurd.repository.entity.Drummer;
import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.service.DrummerSubService;
import com.github.smurd.service.SendBotMessageService;
import org.apache.commons.lang3.ObjectUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.smurd.command.CommandName.ADD_DRUMMER_SUB;
import static com.github.smurd.command.CommandUtils.getChatId;
import static com.github.smurd.command.CommandUtils.getMessage;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Add Drummer subscription {@link Command}.
 */
public class AddDrummerSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final DrummerSubService drummerSubService;
    private final DrummerRepository drummerRepository;

    public AddDrummerSubCommand(SendBotMessageService sendBotMessageService,
            DrummerSubService drummerSubService, DrummerRepository drummerRepository) {
        this.sendBotMessageService = sendBotMessageService;
        this.drummerSubService = drummerSubService;
        this.drummerRepository = drummerRepository;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_DRUMMER_SUB.getCommandName())) {
            sendDrummerWithNoName(getChatId(update));
            return;
        }
        Long chatId = getChatId(update);
        if (getMessage(update).equalsIgnoreCase(ADD_DRUMMER_SUB.getCommandName() +
                SPACE + "all")) {
            //saveAllDrummerSub(chatId); //TODO refactoring saveAllDrummerSub
            return;
        }
        String drummerParam = getMessage(update).split(SPACE, 2)[1];
        if (isNumeric(drummerParam)) {
            saveDrummerSub(chatId, drummerParam);
        } else {
            List<Drummer> drummers = drummerRepository.findByNameContainingIgnoreCase(drummerParam);
            if (ObjectUtils.isEmpty(drummers)) {
                sendDrummerNotFound(chatId, drummerParam, false);
            } else if (drummers.size() == 1) {
                saveDrummerSub(chatId, drummers.get(0).getId().toString());
            } else {
                sendDrummerIdList(chatId, drummers);
            }
        }
    }

    private void sendDrummerIdList(Long chatId, List<Drummer> drummers) {
        String drummerIds = drummers.stream()
                .map(drummer -> String.format("%s - %s %n", drummer.getName(), drummer.getId()))
                .collect(Collectors.joining());

        String message = """
                To subscribe to a drummer, pass the command along with the drummer ID.\s
                For example: /addDrummerSub 203017.\s

                List of found drummers available for subscription\s

                drummer name - drummer ID\s

                %s""";

        sendBotMessageService.sendMessage(chatId, String.format(message, drummerIds));
    }

    private void sendDrummerNotFound(Long chatId, String drummerParam, boolean withId) {
        String drummerNotFoundMessage = "No drummer with %s = \"%s\"";
        String param = withId ? "ID" : "name";
        sendBotMessageService.sendMessage(chatId, String.format(drummerNotFoundMessage, param,
                drummerParam));
    }

    private void sendDrummerWithNoName(Long chatId) {
        String message = """
                To subscribe to a drummer, pass the command along with the drummer's name.\s
                For example: /addDrummerSub Dave Grohl.\s
                
                To subscribe to all available drummers, pass /addDrummerSub all""";

        sendBotMessageService.sendMessage(chatId, message);
    }

    private void saveDrummerSub(Long chatId, String drummerParam) {
        Optional<Drummer> drummerById = drummerRepository.findById(Integer.parseInt(drummerParam));
        if (drummerById.isPresent()) {
            DrummerSub savedDrummerSub = drummerSubService.save(chatId, drummerById.get());
            sendBotMessageService.sendMessage(chatId, "You signed up for a drummer " +
                    savedDrummerSub.getName());
        } else {
            sendDrummerNotFound(chatId, drummerParam, true);
        }
    }

    private void saveAllDrummerSub(Long chatId) {
        List<Drummer> drummers = drummerRepository.findAll();
        if (!ObjectUtils.isEmpty(drummers)) {
            for (Drummer drummer : drummers) {
                drummerSubService.save(chatId, drummer);
            }
            sendBotMessageService.sendMessage(chatId,
                    "You signed up to all available drummers");
        } else {
            sendBotMessageService.sendMessage(chatId, "No subscriptions available");
        }
    }
}
