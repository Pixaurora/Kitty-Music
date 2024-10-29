package net.pixaurora.kitten_heart.impl.scrobble.setup;

import java.io.IOException;

import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;

public interface ScrobblerAuthFunction<T> {
    T createScrobbler(Client client, String token) throws IOException, KitTunesException;
}
