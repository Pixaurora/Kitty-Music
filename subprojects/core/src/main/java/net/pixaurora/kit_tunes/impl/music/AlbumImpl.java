package net.pixaurora.kit_tunes.impl.music;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.NamespacedResourcePath;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kit_tunes.impl.resource.TransformsInto;

public class AlbumImpl implements Album {
	private final String name;
	private final Optional<NamespacedResourcePath> albumArtPath;

	private final List<Track> tracks;

	public AlbumImpl(String name, Optional<NamespacedResourcePath> albumArtPath, List<Track> tracks) {
		this.name = name;
		this.albumArtPath = albumArtPath;
		this.tracks = tracks;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public Optional<NamespacedResourcePath> albumArtPath() {
		return this.albumArtPath;
	}

	@Override
	public List<Track> tracks() {
		return this.tracks;
	}

	public static class Data implements TransformsInto<Album> {
		private final String name;
		@Nullable
		@SerializedName("album_art")
		private final NamespacedResourcePath albumArtPath;

		private final List<TrackImpl.Data> tracks;

		public Data(String name, NamespacedResourcePath albumArtPath, List<TrackImpl.Data> trackData) {
			this.name = name;
			this.albumArtPath = albumArtPath;
			this.tracks = trackData;
		}

		public Album transform(ResourcePath path) {
			List<Track> tracks = new ArrayList<>();

			AlbumImpl album = new AlbumImpl(name, Optional.ofNullable(this.albumArtPath), tracks);

			for (TrackImpl.Data trackData : this.tracks) {
				tracks.add(trackData.transformToTrack(Optional.of(album)));
			}

			return album;
		}
	}

}