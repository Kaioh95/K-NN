package com.app;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

@Setter
@Getter
public class Trainer {
    private CsvHandler csv;

    public Trainer(CsvHandler csv){
        this.csv = csv;
    }

    public void allImagesFeatures() throws InterruptedException {
        FeatureExtraction features = new FeatureExtraction();
        Pattern pattern = Pattern.compile("\\.");
        List<Thread> threads = new ArrayList<>();

        File file = new File(csv.getImgsPath());
        for(File img: Objects.requireNonNull(file.listFiles())){
            String imgKey = Arrays.asList(pattern.split(img.getName(), 2)).get(0);
            System.out.println(imgKey);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    csv.getDataset().get(Integer.parseInt(imgKey))
                            .setFeatures(features
                                    .extract(csv.getImgsPath() + imgKey + ".png"));
                    //System.out.println(Thread.getAllStackTraces().keySet());
                }
            });
            thread.start();
            threads.add(thread);

            if (threads.size() == 50){
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
