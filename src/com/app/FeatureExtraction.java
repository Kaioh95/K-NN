package com.app;

//import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;

import java.util.List;

public class FeatureExtraction {

    /*static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }*/

    public List<Float> extract(String rawImg){
        HOGDescriptor hog = new HOGDescriptor();
        Mat img = new Mat();
        MatOfFloat features = new MatOfFloat();

        try {
            img = Imgcodecs.imread(rawImg, Imgcodecs.IMREAD_GRAYSCALE);
            Imgproc.resize(img, img, new Size(64, 128), 0.5, 0.5, Imgproc.INTER_LINEAR);
            hog.compute(img, features);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return features.toList().subList(0, 1000);
    }
}
