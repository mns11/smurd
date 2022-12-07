package com.github.smurd.service;

import com.github.smurd.repository.entity.Drummer;
import com.github.smurd.repository.entity.DrummerSub;
import java.util.Optional;

/**
 * Service for manipulating with {@link DrummerSub}.
 */
public interface DrummerSubService {

    DrummerSub save(Long chatId, Drummer drummer);

    DrummerSub save(DrummerSub drummerSub);

    Optional<DrummerSub> findById(Integer id);
}
