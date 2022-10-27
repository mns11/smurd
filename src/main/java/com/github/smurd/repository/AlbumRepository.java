package com.github.smurd.repository;

import com.github.smurd.repository.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository} for {@link Album} entity.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
}
