package com.shazamclone.audiomatch.util;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class AudioUtils {
    public static AudioFormat getFormat() {
        return new AudioFormat(44100, 16, 1, true, true);
    }

    public static byte[] captureAudio(int durationInSeconds) throws Exception {
        AudioFormat format = getFormat();
        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(new DataLine.Info(TargetDataLine.class, format));
        line.open(format);
        line.start();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        long end = System.currentTimeMillis() + durationInSeconds * 1000;

        while (System.currentTimeMillis() < end) {
            int count = line.read(buffer, 0, buffer.length);
            if (count > 0) {
                out.write(buffer, 0, count);
            }
        }

        line.stop();
        line.close();
        return out.toByteArray();
    }
}
