package com.app;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.util.Arrays;

public class FeatureExtraction {

    private static final Size SIZE = new Size(64, 128);

    public Byte[] extract(String rawImg){
        Mat img = Imgcodecs.imread(rawImg);
        Mat resizedImage = new Mat();
        Imgproc.resize(img, resizedImage, SIZE);

        byte[] buffer = new byte[(int) (resizedImage.total() * resizedImage.channels())];
        resizedImage.get(0, 0, buffer);
        Byte[] bytes = new Byte[buffer.length];
        Arrays.setAll(bytes, n->buffer[n]);

        img.release();
        resizedImage.release();
        return bytes;
    }
}
