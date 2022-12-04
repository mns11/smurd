package com.github.smurd.command;

import static com.github.smurd.command.CommandName.LIST_DRUMMER_SUB;

import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.repository.entity.TelegramUser;
import com.github.smurd.service.SendBotMessageService;
import com.github.smurd.service.TelegramUserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-level testing for ListDrummerSubCommand")
class ListDrummerSubCommandTest {

    @Test
    void shouldProperlyShowsListDrummerSub() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(true);
        telegramUser.setChatId(1L);

        List<DrummerSub> drummerSubList = new ArrayList<>();
        drummerSubList.add(populateDrummerSub(1, "ds1"));
        drummerSubList.add(populateDrummerSub(2, "ds2"));
        drummerSubList.add(populateDrummerSub(3, "ds3"));
        drummerSubList.add(populateDrummerSub(4, "ds4"));

        telegramUser.setDrummerSubs(drummerSubList);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId()))
                .thenReturn(Optional.of(telegramUser));

        ListDrummerSubCommand command = new ListDrummerSubCommand(sendBotMessageService,
                telegramUserService);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(telegramUser.getChatId());
        Mockito.when(message.getText()).thenReturn(LIST_DRUMMER_SUB.getCommandName());
        update.setMessage(message);

        String collectedDrummers = "List of all your drummer subscriptions: \n\n" +
                telegramUser.getDrummerSubs().stream()
                        .map(it -> "Drummer: " + it.getName() + ", ID = " + it.getId() + " \n")
                        .collect(Collectors.joining());

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(),
                collectedDrummers);
    }

    private DrummerSub populateDrummerSub(Integer id, String name) {
        DrummerSub ds = new DrummerSub();
        ds.setId(id);
        ds.setName(name);
        return ds;
    }
}
