package com.jmh;

import com.app.CsvHandler;
import com.app.Trainer;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class TrainerBrenchMark {
    private CsvHandler trainerHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv",
            "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/");
    private Trainer trainer = new Trainer(trainerHandler);


    @Setup
    public void setUpTrainerBranch(){
        trainerHandler.readCsv(",", 1);
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 4)
    @OutputTimeUnit(TimeUnit.MINUTES)
    public void init() throws InterruptedException {
        trainer.allImagesFeatures();
    }

    @TearDown
    public void TearDownTrainerBranch(){ }
}
