package com.shazamclone.audiomatch.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "datapoints")
public class DataPoint {
    private int time;
    private int songId;
    private long hash;

    public DataPoint(int songId, int time, long hash) {
        this.songId = songId;
        this.time = time;
        this.hash = hash;
    }

    public int getTime() { return time; }
    public int getSongId() { return songId; }
    public long getHash() { return hash; }
}

