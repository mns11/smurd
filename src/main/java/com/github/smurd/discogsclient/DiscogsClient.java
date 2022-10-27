package com.github.smurd.discogsclient;

import com.github.smurd.discogsclient.dto.Artist;

/**
 * Client for Discogs Open API corresponds to Artists.
 */
public interface DiscogsClient {

    /**
     * Get {@link Artist} by ID.
     *
     * @return the {@link Artist} object.
     */
    Artist getArtist(String id);
}
