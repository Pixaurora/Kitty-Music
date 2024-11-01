package net.pixaurora.kitten_heart.impl.scrobble;

import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.music.history.ListenRecord;

public interface SimpleScrobbler {
    public void startScrobbling(Client client, ListenRecord track) throws KitTunesException;

    public void completeScrobbling(Client client, ListenRecord track) throws KitTunesException;
}
