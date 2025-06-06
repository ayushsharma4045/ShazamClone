package com.shazamclone.audiomatch.importer;

import com.shazamclone.audiomatch.service.FingerprintService;
import com.shazamclone.audiomatch.util.FFTUtils;
import com.shazamclone.audiomatch.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SongImporter {
    @Autowired
    private FingerprintService fingerprintService;

    private static final AtomicInteger idGen = new AtomicInteger(1000);

    public void importSongsFromFolder(String folderPath) throws Exception {
        File folder = new File(folderPath);
        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(".wav")) {
                byte[] audio = FileUtils.readAudioBytes(file.getAbsolutePath());
                double[][] spectrogram = FFTUtils.chunkAndFFT(audio, 4096);
                int songId = idGen.incrementAndGet();
                String name = file.getName().replace(".wav", "");
                String artist = "Unknown";
                fingerprintService.extractAndStoreFingerprints(spectrogram, songId, name, artist);
                System.out.println("Imported: " + name);
            }
        }
    }
}
