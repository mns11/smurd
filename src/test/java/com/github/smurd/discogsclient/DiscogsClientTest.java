package com.github.smurd.discogsclient;

import com.github.smurd.discogsclient.dto.Artist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Integration-level testing for DiscogsClientImpl")
class DiscogsClientTest {

    private final DiscogsClient discogsClient = new DiscogsClientImpl("https://api.discogs.com");

    @Test
    public void shouldProperlyGetWithId() {
        //given
        String id = "1";

        //when
        Artist artist = discogsClient.getArtist(id);

        //then
        Assertions.assertNotNull(artist.getName());
    }
}
