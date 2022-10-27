package com.github.smurd.discogsclient;

import com.github.smurd.discogsclient.dto.Artist;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of the {@link DiscogsClient} interface.
 */
@Component
public class DiscogsClientImpl implements DiscogsClient{

    private final String discogsApiArtistPath;

    public DiscogsClientImpl(@Value("${discogs.api.path}") String discogsApi) {
        this.discogsApiArtistPath = discogsApi + "/artists/";
    }

    @Override
    public Artist getArtist(String id) {
        return Unirest.get(discogsApiArtistPath + id)
                .asObject(new GenericType<Artist>() {
                })
                .getBody();
    }
}
