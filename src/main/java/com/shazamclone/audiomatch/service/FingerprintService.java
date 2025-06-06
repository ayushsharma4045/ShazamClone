package com.shazamclone.audiomatch.service;

import com.shazamclone.audiomatch.model.DataPoint;
import com.shazamclone.audiomatch.model.SongMetadata;
import com.shazamclone.audiomatch.repository.FingerprintRepository;
import com.shazamclone.audiomatch.repository.SongRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FingerprintService {
    @Autowired
    private FingerprintRepository fingerprintRepository;

    @Autowired
    private SongRepository songRepository;

    private static final int[] RANGE = new int[]{40, 80, 120, 180, 300};
    private static final int FUZ_FACTOR = 2;

    public void extractAndStoreFingerprints(double[][] spectrogram, int songId, String name, String artist) {
        int[][] points = new int[spectrogram.length][20];
        double[][] highscores = new double[spectrogram.length][20];

        for (int t = 0; t < spectrogram.length; t++) {
            for (int freq = 40; freq < 300; freq++) {
                double mag = Math.log(spectrogram[t][freq] + 1);
                int index = getIndex(freq);
                if (mag > highscores[t][index]) {
                    highscores[t][index] = mag;
                    points[t][index] = freq;
                }
            }
        }

        List<DataPoint> dataPoints = new ArrayList<>();
        for (int t = 0; t < points.length; t++) {
            long hash = hash(points[t][0], points[t][1], points[t][2], points[t][3]);
            dataPoints.add(new DataPoint(songId, t, hash));
        }
        fingerprintRepository.saveAll(dataPoints);
        songRepository.save(new SongMetadata(songId, name, artist));
    }

    public void matchFingerprints(double[][] spectrogram) {
        int[][] points = new int[spectrogram.length][20];
        double[][] highscores = new double[spectrogram.length][20];
        Map<Integer, Integer> matches = new HashMap<>();

        for (int t = 0; t < spectrogram.length; t++) {
            for (int freq = 40; freq < 300; freq++) {
                double mag = Math.log(spectrogram[t][freq] + 1);
                int index = getIndex(freq);
                if (mag > highscores[t][index]) {
                    highscores[t][index] = mag;
                    points[t][index] = freq;
                }
            }
        }

        for (int t = 0; t < points.length; t++) {
            long hash = hash(points[t][0], points[t][1], points[t][2], points[t][3]);
            List<DataPoint> candidates = fingerprintRepository.findByHash(hash);
            for (DataPoint dp : candidates) {
                matches.put(dp.getSongId(), matches.getOrDefault(dp.getSongId(), 0) + 1);
            }
        }

        int bestMatch = -1, max = 0;
        for (Map.Entry<Integer, Integer> e : matches.entrySet()) {
            if (e.getValue() > max) {
                bestMatch = e.getKey();
                max = e.getValue();
            }
        }

        if (bestMatch != -1) {
            Optional<SongMetadata> songOpt = songRepository.findById(bestMatch);
            songOpt.ifPresent(song -> System.out.println("Match found: " + song.getName() + " by " + song.getArtist()));
        } else {
            System.out.println("No match found.");
        }
    }

    private int getIndex(int freq) {
        int i = 0;
        while (i < RANGE.length && RANGE[i] < freq) i++;
        return i;
    }

    private long hash(long p1, long p2, long p3, long p4) {
        return (p4 - (p4 % FUZ_FACTOR)) * 100000000L + (p3 - (p3 % FUZ_FACTOR)) * 100000L +
                (p2 - (p2 % FUZ_FACTOR)) * 100 + (p1 - (p1 % FUZ_FACTOR));
    }
}
