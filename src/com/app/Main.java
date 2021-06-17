package com.app;

import org.opencv.core.Core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {
        var arquivo = new CSV_Reader();
        arquivo.readData("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv", ",", 0);
	    String trainingPath = "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/";

        arquivo.featuresToCSV(trainingPath,"trainingFeature.csv", true);

        CSV_Reader arquivo1 = new CSV_Reader();

        arquivo.readData("trainingFeature.csv", ";", 1);
        arquivo1.readData("/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset.csv", ",", 1);
        String testPath = "/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset/boneage-test-dataset/";

        FileWriter arq = new FileWriter("output.csv");
        PrintWriter arqWrite = new PrintWriter(arq);
        FeatureExtraction features = new FeatureExtraction();
        List<Float> imgFeatures;
        KNN knn = new KNN();

        for (int i = 0; i < arquivo1.dataset.size(); i++) {
            System.out.println(i);
            if (i == 0) {
                arqWrite.append(String.join(";", arquivo1.dataset.get(i)) + "PredictedAge\n");
            } else {
                imgFeatures = features.extract(testPath + arquivo1.dataset.get(i)[0] + ".png");
                knn.k_nn_exec(arquivo.dataset, imgFeatures, 6, 0);

                arqWrite.append(String.join(";", arquivo1.dataset.get(i)) + ";");
                arqWrite.append(knn.predict() + "\n");
            }
        }
        arqWrite.close();

    }

    public static List<Path> listFiles(Path path) throws IOException {

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;

        /*List<Path> allImages= listFiles(Path.of(trainingPath));
        for (Path image : allImages) {
            arquivo.featuresToCSV(image.toString(), 1,"dataproc.csv", false);
        }*/
    }


}
