package com.jmh;

import com.app.CsvHandler;
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

public class JMHCsvHandler {

    @State(Scope.Thread)
    public static class CsvState{
        @Setup(Level.Trial)
        public void doSetup() {
            csvHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv",
                    "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/");
        }

        @TearDown(Level.Trial)
        public void doTearDown() {
            csvHandler.setDataset(null);
        }

        CsvHandler csvHandler = new CsvHandler("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset.csv",
                "/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/");
    }

    @Benchmark
    public void csvReadBench(CsvState state){
        state.csvHandler.readCsv(",", 1);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHCsvHandler.class.getSimpleName())
                .threads(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
