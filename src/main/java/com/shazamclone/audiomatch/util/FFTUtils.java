package com.shazamclone.audiomatch.util;

import org.jtransforms.fft.DoubleFFT_1D;

public class FFTUtils {
    public static double[][] chunkAndFFT(byte[] audio, int chunkSize) {
        int totalSamples = audio.length / 2;
        int numChunks = totalSamples / chunkSize;
        double[][] spectrogram = new double[numChunks][chunkSize * 2];

        for (int i = 0; i < numChunks; i++) {
            double[] signal = new double[chunkSize * 2];
            for (int j = 0; j < chunkSize; j++) {
                int index = (i * chunkSize + j) * 2;
                if (index + 1 < audio.length) {
                    int sample = ((audio[index + 1] << 8) | (audio[index] & 0xff));
                    signal[j] = sample;
                }
            }
            DoubleFFT_1D fft = new DoubleFFT_1D(chunkSize);
            fft.realForwardFull(signal);
            spectrogram[i] = signal;
        }
        return spectrogram;
    }
}
