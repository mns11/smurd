package com.github.smurd.command;

import static com.github.smurd.command.AbstractCommandTest.prepareUpdate;
import static com.github.smurd.command.CommandName.DELETE_DRUMMER_SUB;
import static java.util.Collections.singletonList;

import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.repository.entity.TelegramUser;
import com.github.smurd.service.DrummerSubService;
import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-level testing for DeleteDrummerSubCommand")
class DeleteDrummerSubCommandTest {

    private Command command;
    private SendBotMessageService sendBotMessageService;
    DrummerSubService drummerSubService;
    TelegramUserService telegramUserService;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        drummerSubService = Mockito.mock(DrummerSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);

        command = new DeleteDrummerSubCommand(sendBotMessageService, drummerSubService,
                telegramUserService);
    }

    @Test
    void shouldProperlyReturnEmptySubscriptionList() {
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_DRUMMER_SUB.getCommandName());

        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(new TelegramUser()));

        String expectedMessage = "There are no drummer subscriptions yet. "
                + "To add a subscription write /addGroupSub";

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    void shouldProperlyReturnSubscriptionList() {
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, DELETE_DRUMMER_SUB.getCommandName());
        TelegramUser telegramUser = new TelegramUser();
        DrummerSub ds1 = new DrummerSub();
        ds1.setId(123);
        ds1.setName("DS1 Name");
        telegramUser.setDrummerSubs(singletonList(ds1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = """
                    To remove a subscription to a drummer - \s
                    send the command line along with the drummer ID.\s
                    For example: /deleteDrummerSub 11\s

                    List of all drummers you are subscribed to\s

                    drummer name - drummer ID\s

                    DS1 Name - 123 \n""";

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    void shouldRejectByInvalidDrummerId() {
        //given
        Long chatId = 23456L;
        Update update = prepareUpdate(chatId, String.format("%s %s",
                DELETE_DRUMMER_SUB.getCommandName(), "drummerSubId"));
        TelegramUser telegramUser = new TelegramUser();
        DrummerSub ds1 = new DrummerSub();
        ds1.setId(123);
        ds1.setName("DS1 Name");
        telegramUser.setDrummerSubs(singletonList(ds1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = "Wrong drummer ID format.\n ID must be a positive integer.";

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    void shouldProperlyDeleteByDrummerId() {
        //given

        /// prepare update object
        Long chatId = 23456L;
        Integer drummerId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s",
                DELETE_DRUMMER_SUB.getCommandName(), drummerId));

        DrummerSub ds1 = new DrummerSub();
        ds1.setId(123);
        ds1.setName("DS1 Name");
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        telegramUser.setDrummerSubs(singletonList(ds1));
        ArrayList<TelegramUser> users = new ArrayList<>();
        users.add(telegramUser);
        ds1.setUsers(users);
        Mockito.when(drummerSubService.findById(drummerId)).thenReturn(Optional.of(ds1));
        Mockito.when(telegramUserService.findByChatId(chatId))
                .thenReturn(Optional.of(telegramUser));

        String expectedMessage = "Unsubscribed from the drummer: DS1 Name";

        //when
        command.execute(update);

        //then
        users.remove(telegramUser);
        Mockito.verify(drummerSubService).save(ds1);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }

    @Test
    void shouldDoesNotExistByDrummerId() {
        //given
        Long chatId = 23456L;
        Integer drummerId = 1234;
        Update update = prepareUpdate(chatId, String.format("%s %s",
                DELETE_DRUMMER_SUB.getCommandName(), drummerId));

        Mockito.when(drummerSubService.findById(drummerId)).thenReturn(Optional.empty());

        String expectedMessage = "Drummer not found in subscriptions =/";

        //when
        command.execute(update);

        //then
        Mockito.verify(drummerSubService).findById(drummerId);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, expectedMessage);
    }
}
