package net.pixaurora.kitten_heart.impl.music.metadata;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Artist;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.service.MusicMetadataService;

public class MusicMetadataImpl implements MusicMetadataService, MutableMusicMetadata {
    private final Map<ResourcePath, Album> albums = new HashMap<>();
    private final Map<ResourcePath, Artist> artists = new HashMap<>();
    private final Map<ResourcePath, Track> tracks = new HashMap<>();

    private final HashMap<String, Track> trackMatches = new HashMap<>();
    private final HashMap<ResourcePath, List<Album>> trackToAlbums = new HashMap<>();

    @Override
    public void add(Album album) {
        this.albums.put(album.path(), album);

        for (Track track : album.tracks()) {
            this.getAlbums(track).add(album);
        }
    }

    @Override
    public void add(Artist artist) {
        this.artists.put(artist.path(), artist);
    }

    @Override
    public void add(Track track) {
        this.tracks.put(track.path(), track);

        for (String trackMatch : track.matches()) {
            this.trackMatches.put(trackMatch, track);
        }
    }

    @Override
    public void load(List<Path> albumFiles, List<Path> artistFiles, List<Path> trackFiles) {
        MusicMetadataLoader.load(this, albumFiles, artistFiles, trackFiles);
    }

    @Override
    public Optional<Artist> getArtist(ResourcePath path) {
        return Optional.ofNullable(artists.get(path));
    }

    @Override
    public Optional<Track> matchTrack(ResourcePath soundPath) {
        String[] splitPath = soundPath.representation().split("/");
        String filename = splitPath[splitPath.length - 1];

        return Optional.ofNullable(trackMatches.get(filename));
    }

    @Override
    public List<Album> albumsWithTrack(Track track) {
        return getAlbums(track);
    }

    private List<Album> getAlbums(Track track) {
        return trackToAlbums.computeIfAbsent(track.path(), path -> new ArrayList<>());
    }

    @Override
    public Optional<Track> getTrack(ResourcePath path) {
        return Optional.ofNullable(tracks.get(path));
    }

}
