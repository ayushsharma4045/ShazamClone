package com.shazamclone.audiomatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shazamclone.audiomatch.importer.SongImporter;
import com.shazamclone.audiomatch.service.FingerprintService;
import com.shazamclone.audiomatch.util.AudioUtils;
import com.shazamclone.audiomatch.util.FFTUtils;

@SpringBootApplication
public class AudiomatchApplication implements CommandLineRunner {

	@Autowired
    private SongImporter songImporter;

	@Autowired
	private FingerprintService fingerprintService;

	public static void main(String[] args) {
		SpringApplication.run(AudiomatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0 && args[0].equals("import")) {
            songImporter.importSongsFromFolder("src/main/resources/songs");
        } else {
            System.out.println("Recording...");
            byte[] audio = AudioUtils.captureAudio(20);
            double[][] spectrogram = FFTUtils.chunkAndFFT(audio, 4096);
            fingerprintService.matchFingerprints(spectrogram);
        }
	}
}
