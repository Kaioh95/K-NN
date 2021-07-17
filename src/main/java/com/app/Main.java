package com.app;

import org.opencv.core.Core;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static{
        nu.pattern.OpenCV.loadLocally();
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws InterruptedException {
        CsvHandler trainerHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv",
                "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/");
        trainerHandler.readCsv(",", 1);

        Trainer trainer = new Trainer(trainerHandler);
        trainer.allImagesFeatures();

        List<ImageData> trainingList = new ArrayList<>(trainerHandler.getDataset().values());

        InputImages inImgs = new InputImages();
        inImgs.predictInputImages(trainingList);
    }

}
