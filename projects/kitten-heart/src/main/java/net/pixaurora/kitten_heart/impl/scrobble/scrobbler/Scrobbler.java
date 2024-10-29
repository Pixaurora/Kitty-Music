package net.pixaurora.kitten_heart.impl.scrobble.scrobbler;

import java.io.IOException;
import java.util.Arrays;

import net.pixaurora.catculator.api.http.Client;
import net.pixaurora.kitten_heart.impl.config.dispatch.DispatchGroup;
import net.pixaurora.kitten_heart.impl.config.dispatch.SpecifiesType;
import net.pixaurora.kitten_heart.impl.error.KitTunesException;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerType;
import net.pixaurora.kitten_heart.impl.scrobble.SimpleScrobbler;

public interface Scrobbler extends SimpleScrobbler, SpecifiesType<Scrobbler> {
    public static final DispatchGroup<Scrobbler, ScrobblerType<? extends Scrobbler>> TYPES = new DispatchGroup<>(
            "scrobbler", Arrays.asList(LastFMScrobbler.TYPE, LegacyLastFMScrobbler.TYPE));

    public String username(Client client) throws IOException, KitTunesException;
}
