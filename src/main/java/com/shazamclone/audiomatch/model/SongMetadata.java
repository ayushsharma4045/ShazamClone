package com.shazamclone.audiomatch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "songs")
public class SongMetadata {
    @Id
    private int songId;
    private String name;
    private String artist;

    public SongMetadata(int songId, String name, String artist) {
        this.songId = songId;
        this.name = name;
        this.artist = artist;
    }

    public int getSongId() { return songId; }
    public String getName() { return name; }
    public String getArtist() { return artist; }
}