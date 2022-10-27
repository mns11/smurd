package com.github.smurd.repository;

import com.github.smurd.repository.entity.Drummer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link Repository} for {@link Drummer} entity.
 */
@Repository
public interface DrummerRepository extends JpaRepository<Drummer, Integer> {

    List<Drummer> findByNameContainingIgnoreCase(String name);
}
