package com.jmh;

import com.app.EuclideanDistance;
import com.app.FeatureExtraction;
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


public class JMHEuDistance {

    @State(Scope.Thread)
    public static class DistanceState{
        FeatureExtraction ft;
        Byte[] img1;
        Byte[] img2;
        EuclideanDistance distance;

        @Setup(Level.Trial)
        public void doSetUp() throws InterruptedException {
            ft = new FeatureExtraction();
            distance = new EuclideanDistance();
            img1 = ft.extract("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/1377.png");
            img2 = ft.extract("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/1378.png");
        }
        @TearDown(Level.Trial)
        public void doTearDown(){
            img1 = null;
            img2 = null;
        }
    }

    @Benchmark
    public void EuDistanceBench(DistanceState state){
        state.distance.distance(state.img1, state.img2);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHEuDistance.class.getSimpleName())
                .threads(1)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
