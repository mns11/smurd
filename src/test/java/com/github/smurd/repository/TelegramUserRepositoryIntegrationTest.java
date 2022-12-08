package com.github.smurd.repository;

import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Integration-level testing for {@link TelegramUserRepository}.
 */
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class TelegramUserRepositoryIntegrationTest {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/telegram_users.sql"})
    @Test
    void shouldProperlyFindAllActiveUsers() {
        //when
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        //then
        Assertions.assertEquals(5, users.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    void shouldProperlySaveTelegramUser() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(1234567890L);
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);

        //when
        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());

        //then
        Assertions.assertTrue(saved.isPresent());
        Assertions.assertEquals(telegramUser, saved.get());
    }

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveDrummerSubsForUser.sql"})
    @Test
    void shouldProperlyGetAllDrummerSubsForUser() {
        //when
        Optional<TelegramUser> userFromDB = telegramUserRepository.findById(1L);

        //then
        Assertions.assertTrue(userFromDB.isPresent());
        List<DrummerSub> drummerSubs = userFromDB.get().getDrummerSubs();
        for (int i = 0; i < drummerSubs.size(); i++) {
            Assertions.assertEquals(String.format("drummer%s", (i + 1)),
                    drummerSubs.get(i).getName());
            Assertions.assertEquals(i + 1, drummerSubs.get(i).getId());
            Assertions.assertEquals(i + 1, drummerSubs.get(i).getLastReleaseId());
        }
    }
}
