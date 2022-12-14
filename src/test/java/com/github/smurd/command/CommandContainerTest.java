package com.github.smurd.command;

import com.github.smurd.repository.DrummerRepository;
import com.github.smurd.service.DrummerSubService;
import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        DrummerSubService drummerSubService = Mockito.mock(DrummerSubService.class);
        DrummerRepository drummerRepository = Mockito.mock(DrummerRepository.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService,
                drummerSubService, drummerRepository);
    }

    @Test
    void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/test";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}
