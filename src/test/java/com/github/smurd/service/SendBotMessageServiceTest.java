package com.github.smurd.service;

import com.github.smurd.bot.SmurdTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private SmurdTelegramBot smurdTelegramBot;

    @BeforeEach
    public void init() {
        smurdTelegramBot = Mockito.mock(SmurdTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(smurdTelegramBot);
    }

    @Test
    void shouldProperlySendMessage() throws TelegramApiException {
        //given
        Long chatId = 123L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(smurdTelegramBot).execute(sendMessage);
    }
}
