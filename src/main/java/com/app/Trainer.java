package com.app;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
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

        List<Thread> threads = new ArrayList<>();

        File file = new File(csv.getImgsPath());
        for(File img: Objects.requireNonNull(file.listFiles())){
            String imgKey = Arrays.asList(img.getName().split("\\.")).get(0);
            System.out.println(imgKey);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    FeatureExtraction features = new FeatureExtraction();
                    List<Float> imgFeatures;
                    imgFeatures = features.extract(csv.getImgsPath() + imgKey + ".png");

                    for (Float imgFeature : imgFeatures) {
                        csv.getDataset().get(imgKey).add(String.valueOf(imgFeature));
                    }
                    //System.out.println(Thread.getAllStackTraces().keySet());
                }
            });
            thread.start();
            threads.add(thread);

            if (threads.size() == 200){
                for( Thread innerThread: threads)
                    innerThread.join();
                threads.clear();
            }
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

}
