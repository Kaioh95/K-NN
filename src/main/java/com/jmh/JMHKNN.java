package com.jmh;

import com.app.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;

public class JMHKNN {

    @State(Scope.Thread)
    public static class knnState{
        CsvHandler trainerHandler;
        Trainer trainer;

        FeatureExtraction features = new FeatureExtraction();
        Byte[] imgFeatures;
        KNN knn = new KNN();
        List<ImageData> trainingList = new ArrayList<>();

        @Setup(Level.Trial)
        public void doSetup() throws InterruptedException {
            trainerHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv",
                    "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/");
            trainerHandler.readCsv(",", 1);
            trainer = new Trainer(trainerHandler);
            trainer.allImagesFeatures();

            trainingList = new ArrayList<>(trainerHandler.getDataset().values());
            imgFeatures = features.extract("/home/kaio/IdeaProjects/boneage_dataset/boneage-test-dataset/boneage-test-dataset/4400.png");
        }

        /*@TearDown(Level.Trial)
        public void doTearDown(){
            trainerHandler.setDataset(null);
            trainer.setCsv(null);
            trainingList = null;
        }*/
    }

    @Benchmark
    public void knnBench(knnState state){
        state.knn.knnExec(state.trainingList, state.imgFeatures, 6);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHKNN.class.getSimpleName())
                .threads(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
