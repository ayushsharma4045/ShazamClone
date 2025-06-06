package com.shazamclone.audiomatch.util;

import javax.sound.sampled.*;
import java.io.File;

public class FileUtils {
    public static byte[] readAudioBytes(String filePath) throws Exception {
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filePath));
        AudioFormat format = ais.getFormat();
        byte[] audio = ais.readAllBytes();
        ais.close();
        return audio;
    }
}
