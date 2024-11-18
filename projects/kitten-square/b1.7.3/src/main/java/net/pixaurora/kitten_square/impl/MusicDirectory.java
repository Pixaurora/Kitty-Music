package net.pixaurora.kitten_square.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public enum MusicDirectory {
    OLD("music", "calm"),
    NEW("newmusic", "piano", "nuance", "hal"),
    RECORDS("streaming", "cat", "13");

    Path dir;
    List<String> filePrefixes;

    MusicDirectory(String dir, String... namePrefixes) {
        this.dir = Paths.get("resources").resolve(dir);
        this.filePrefixes = Arrays.asList(namePrefixes);
    }

    public static MusicDirectory containingPrefixOf(String name) {
        for (MusicDirectory folder : values()) {
            if (folder.containsPrefixOf(name)) {
                return folder;
            }
        }

        throw new RuntimeException("Couldn't find directory for track `" + name + "`!");
    }

    public static Path forPath(String name) {
        return MusicDirectory.containingPrefixOf(name).dir().resolve(name);
    }

    public boolean containsPrefixOf(String name) {
        for (String prefix : filePrefixes) {
            if (name.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    public Path dir() {
        return this.dir;
    }
}
