package com.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InputImages {

    public InputImages(){ }

    public void predictInputImages(List<ImageData> trainingList) {
        CsvHandler testHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset.csv",
                "/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset/boneage-test-dataset/");
        testHandler.readCsv(",", 1);

        try {
            PrintWriter arqWrite = new PrintWriter(new FileWriter("output.csv", false) );
            FeatureExtraction features = new FeatureExtraction();
            Byte[] imgFeatures;
            KNN knn = new KNN();

            arqWrite.append("Case ID;Sex;PredictedAge\n");
            File file = new File(testHandler.getImgsPath());
            for (File img : Objects.requireNonNull(file.listFiles())) {
                String imgKey = Arrays.asList(img.getName().split("\\.")).get(0);
                System.out.println(imgKey);

                imgFeatures = features.extract(testHandler.getImgsPath() + img.getName());
                knn.knnExec(trainingList, imgFeatures, 6);
                arqWrite.append(testHandler.getDataset()
                        .get(Integer.parseInt(imgKey)).toCsvWrite() + ";");
                arqWrite.append(knn.predict() + "\n");

            }
            arqWrite.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
