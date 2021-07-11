package com.app;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Setter
@Getter
public class Trainer {
    private CsvHandler csv;

    public Trainer(CsvHandler csv){
        this.csv = csv;
    }

    public void allImagesFeatures() throws InterruptedException {
        FeatureExtraction features = new FeatureExtraction();
        Byte[] imgFeatures;

        File file = new File(csv.getImgsPath());
        for(File img: Objects.requireNonNull(file.listFiles())){
            String imgKey = Arrays.asList(img.getName().split("\\.")).get(0);
            System.out.println(imgKey);

            imgFeatures = features.extract(csv.getImgsPath() + imgKey + ".png");
            csv.getDataset().get(Integer.parseInt(imgKey)).setFeatures(imgFeatures);
            //System.out.println(Thread.getAllStackTraces().keySet());
        }
    }

}
