package com.jmh;

import com.app.CsvHandler;
import com.app.Trainer;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/*@State(Scope.Benchmark)*/
public class TrainerBrenchMark {

    @State(Scope.Thread)
    public static class MyState{
        public CsvHandler trainerHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv",
                "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/");
        public Trainer trainer = new Trainer(trainerHandler);

        @Setup(Level.Trial)
        public void setUpTrainerBrench(){
            trainerHandler.readCsv(",", 1);
            trainer = new Trainer(trainerHandler);
        }

        @TearDown(Level.Trial)
        public void tearDownTrainerBrench(){
            trainerHandler.setDataset(null);
            trainer.setCsv(null);
        }

    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @Warmup(iterations = 1)
    @Measurement(iterations = 4)
    @OutputTimeUnit(TimeUnit.MINUTES)
    public void init(MyState state) throws InterruptedException {
        state.trainer.allImagesFeatures();
    }

}
