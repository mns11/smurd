package com.github.smurd.service;

import com.github.smurd.repository.DrummerSubRepository;
import com.github.smurd.repository.entity.Drummer;
import com.github.smurd.repository.entity.DrummerSub;
import com.github.smurd.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class DrummerSubServiceImpl implements DrummerSubService{

    private final DrummerSubRepository drummerSubRepository;
    private final TelegramUserService telegramUserService;

    @Autowired
    public DrummerSubServiceImpl(DrummerSubRepository drummerSubRepository,
            TelegramUserService telegramUserService) {
        this.drummerSubRepository = drummerSubRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public DrummerSub save(Long chatId, Drummer drummer) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new);
        //TODO add exception handling
        DrummerSub drummerSub;
        Optional<DrummerSub> drummerSubFromDB = drummerSubRepository.findById(drummer.getId());
        if (drummerSubFromDB.isPresent()) {
            drummerSub = drummerSubFromDB.get();
            Optional<TelegramUser> first = drummerSub.getUsers().stream()
                    .filter(it -> it.getChatId().equals(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                drummerSub.addUser(telegramUser);
            }
        } else {
            drummerSub = new DrummerSub();
            drummerSub.addUser(telegramUser);
            drummerSub.setId(drummer.getId());
            drummerSub.setName(drummer.getName());
        }

        return drummerSubRepository.save(drummerSub);
    }
}
