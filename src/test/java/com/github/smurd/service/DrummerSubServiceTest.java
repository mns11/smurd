package com.github.smurd.service;

import com.github.smurd.repository.DrummerRepository;
import com.github.smurd.repository.DrummerSubRepository;
import com.github.smurd.repository.entity.Drummer;
import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.repository.entity.TelegramUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for DrummerSubService")
@Slf4j
class DrummerSubServiceTest {

    private DrummerSubService drummerSubService;
    private DrummerSubRepository drummerSubRepository;
    private TelegramUser newUser;
    private DrummerRepository drummerRepository;

    private final static Long CHAT_ID = 1234234L;

    @BeforeEach
    public void init() {
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        drummerSubRepository = Mockito.mock(DrummerSubRepository.class);
        drummerRepository = Mockito.mock(DrummerRepository.class);
        drummerSubService = new DrummerSubServiceImpl(drummerSubRepository, telegramUserService);

        newUser = new TelegramUser();
        newUser.setActive(true);
        newUser.setChatId(CHAT_ID);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));

        Drummer drummer = new Drummer();
        drummer.setId(1);
        drummer.setName("Drummer1");

        Mockito.when(drummerRepository.findById(1)).thenReturn(Optional.of(drummer));
    }

    @Test
    void shouldProperlySaveDrummer() {
        //given
        Optional<Drummer> drummerFromDB = drummerRepository.findById(1);
        if (drummerFromDB.isPresent()) {
            DrummerSub expectedDrummerSub = new DrummerSub();
            expectedDrummerSub.setId(drummerFromDB.get().getId());
            expectedDrummerSub.setName(drummerFromDB.get().getName());
            expectedDrummerSub.addUser(newUser);

            //when
            drummerSubService.save(CHAT_ID, drummerFromDB.get());

            //then
            Mockito.verify(drummerSubRepository).save(expectedDrummerSub);
        } else {
            log.warn("drummer is not found in DB. test skipped...");
        }
    }

    @Test
    void shouldProperlyAddUserToExistingDrummer() {
        //given
        TelegramUser oldTelegramUser = new TelegramUser();
        oldTelegramUser.setChatId(2L);
        oldTelegramUser.setActive(true);

        Optional<Drummer> drummerFromDB = drummerRepository.findById(1);
        if (drummerFromDB.isPresent()) {
            DrummerSub drummerSubFromDB = new DrummerSub();
            drummerSubFromDB.setId(drummerFromDB.get().getId());
            drummerSubFromDB.setName(drummerFromDB.get().getName());
            drummerSubFromDB.addUser(oldTelegramUser);

            Mockito.when(drummerSubRepository.findById(drummerFromDB.get().getId())).thenReturn(
                    Optional.of(drummerSubFromDB));

            DrummerSub expectedDrummerSub = new DrummerSub();
            expectedDrummerSub.setId(drummerFromDB.get().getId());
            expectedDrummerSub.setName(drummerFromDB.get().getName());
            expectedDrummerSub.addUser(oldTelegramUser);
            expectedDrummerSub.addUser(newUser);

            //when
            drummerSubService.save(CHAT_ID, drummerFromDB.get());

            //then
            Mockito.verify(drummerSubRepository).findById(drummerFromDB.get().getId());
            Mockito.verify(drummerSubRepository).save(expectedDrummerSub);
        } else {
            log.warn("drummer is not found in DB. test skipped...");
        }
    }
}
