package com.github.smurd.command;

import com.github.smurd.repository.DrummerRepository;
import com.github.smurd.service.DrummerSubService;
import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import static com.github.smurd.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling Telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService,
            TelegramUserService telegramUserService, DrummerSubService drummerSubService,
            DrummerRepository drummerRepository) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService,
                        telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService,
                        telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService,
                        telegramUserService))
                .put(ADD_DRUMMER_SUB.getCommandName(), new AddDrummerSubCommand(
                        sendBotMessageService, drummerSubService, drummerRepository))
                .put(LIST_DRUMMER_SUB.getCommandName(), new ListDrummerSubCommand(
                        sendBotMessageService, telegramUserService))
                .put(DELETE_DRUMMER_SUB.getCommandName(), new DeleteDrummerSubCommand(
                        sendBotMessageService, drummerSubService, telegramUserService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
