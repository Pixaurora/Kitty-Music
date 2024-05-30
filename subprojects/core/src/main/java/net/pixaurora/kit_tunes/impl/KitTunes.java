package net.pixaurora.kit_tunes.impl;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.impl.concurrent.KitTunesThreadFactory;
import net.pixaurora.kit_tunes.impl.config.ConfigManager;
import net.pixaurora.kit_tunes.impl.config.ScrobblerCache;
import net.pixaurora.kit_tunes.impl.service.KitTunesMinecraftUICompat;
import net.pixaurora.kit_tunes.impl.service.ServiceLoading;

public class KitTunes {
	public static final Logger LOGGER = LoggerFactory.getLogger(KitTunesConstants.MOD_ID);

	public static final ConfigManager<ScrobblerCache> SCROBBLER_CACHE = new ConfigManager<>(
		KitTunesConstants.SCROBBLER_CACHE_PATH,
		ScrobblerCache.class,
		() -> new ScrobblerCache(List.of())
	);

	public static final KitTunesMinecraftUICompat UI_LAYER = ServiceLoading.loadJustOneOrThrow(KitTunesMinecraftUICompat.class);

	public static final List<MusicEventListener> MUSIC_LISTENERS = ServiceLoading.loadAll(MusicEventListener.class);

	public static final Executor EXECUTOR = Executors.newFixedThreadPool(1, new KitTunesThreadFactory());

	public static final NamespacedResourcePath resource(String path) {
		return NamespacedResourcePath.fromString(KitTunesConstants.MOD_ID + ":" + path);
	}
}