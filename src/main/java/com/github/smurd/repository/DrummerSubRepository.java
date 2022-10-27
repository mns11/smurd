package com.github.smurd.repository;

import com.github.smurd.repository.entity.DrummerSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository} for {@link DrummerSub} entity.
 */
@Repository
public interface DrummerSubRepository extends JpaRepository<DrummerSub, Integer> {
}
