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
 * Integration-level testing for {@link DrummerSubRepository}.
 */
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class DrummerSubRepositoryIT {

    @Autowired
    private DrummerSubRepository drummerSubRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveUsersForDrummerSub.sql"})
    @Test
    public void shouldProperlyGetAllUsersForDrummerSub() {
        //when
        Optional<DrummerSub> drummerSubFromDB = drummerSubRepository.findById(1);

        //then
        Assertions.assertTrue(drummerSubFromDB.isPresent());
        Assertions.assertEquals(1, drummerSubFromDB.get().getId());
        List<TelegramUser> users = drummerSubFromDB.get().getUsers();
        for (int i = 0; i < users.size(); i++) {
            Assertions.assertEquals(Long.valueOf(i + 1), users.get(i).getChatId());
            Assertions.assertTrue(users.get(i).isActive());
        }
    }
}
