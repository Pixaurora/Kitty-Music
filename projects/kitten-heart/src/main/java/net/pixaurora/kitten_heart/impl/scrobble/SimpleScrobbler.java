package net.pixaurora.kitten_heart.impl.scrobble;

import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;

public interface SimpleScrobbler {
    public void startScrobbling(Client client, ScrobbleInfo track) throws KitTunesException;

    public void completeScrobbling(Client client, ScrobbleInfo track) throws KitTunesException;
}
