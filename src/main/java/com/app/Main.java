package com.app;

import org.opencv.core.Core;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {
        var trainnerHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv",
                "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/");

        trainnerHandler.readCsv(",", 1);
        trainnerHandler.allImagesFeatures();

        CsvHandler testHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset.csv",
                "/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset/boneage-test-dataset/");
        testHandler.readCsv(",", 1);

        PrintWriter arqWrite = new PrintWriter(new FileWriter("output.csv") );
        FeatureExtraction features = new FeatureExtraction();
        List<Float> imgFeatures;
        KNN knn = new KNN();

        arqWrite.append("Case ID;Sex;PredictedAge\n");
        File file = new File(testHandler.getImgsPath());
        List<List<String>> trainingList = new ArrayList<>(trainnerHandler.getDataset().values());
        for(File img: Objects.requireNonNull(file.listFiles())){
            String imgKey = Arrays.asList(img.getName().split("\\.")).get(0);
            System.out.println(imgKey);
            imgFeatures = features.extract(testHandler.getImgsPath() + img.getName());

            knn.knnExec(trainingList, imgFeatures, 6, 0);

            arqWrite.append(imgKey + ";" + testHandler.getDataset().get(imgKey).get(0) + ";");
            arqWrite.append(knn.predict() + "\n");
        }
        arqWrite.close();

    }


}
