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
        String trainingPath = "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset";
        String testPath = "/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset";
        arquivo.readData(trainingPath+".csv", ",", 0);

        //arquivo.featuresToCSV(trainingPath+"/boneage-training-dataset/","trainingFeature", true);

        CSV_Reader arquivoF = new CSV_Reader();
        CSV_Reader arquivoM = new CSV_Reader();

        arquivoF.readData("trainingFeatureF.csv", ";", 1);
        arquivoM.readData("trainingFeatureM.csv", ";", 1);
        arquivo.readData(testPath+".csv", ",", 0);

        PrintWriter arqWrite = new PrintWriter(new FileWriter("output.csv") );
        FeatureExtraction features = new FeatureExtraction();
        List<Float> imgFeatures;
        KNN knn = new KNN();

        arqWrite.append(String.join(";", arquivo.dataset.get(0)) + ";PredictedAge\n");
        for (int i = 1; i < arquivo.dataset.size(); i++) {
            System.out.println(i);
            imgFeatures = features.extract(testPath+"/boneage-test-dataset/"+ arquivo.dataset.get(i)[0] + ".png");

            if(arquivo.dataset.get(i)[1].equals("M"))
                knn.k_nn_exec(arquivoM.dataset, imgFeatures, 6, 0);
            else
                knn.k_nn_exec(arquivoF.dataset, imgFeatures, 6, 0);

            arqWrite.append(String.join(";", arquivo.dataset.get(i)) + ";");
            arqWrite.append(knn.predict() + "\n");
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
