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
        FeatureExtraction ft = new FeatureExtraction();
        Byte[] img1;
        Byte[] img2;
        EuclideanDistance distance = new EuclideanDistance();

        @Setup(Level.Trial)
        public void doSetUp(){
            img1 = ft.extract("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/5471.png");
            img1 = ft.extract("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/5582.png");
        }
        @TearDown(Level.Trial)
        public void doTearDown(){
            img1 = null;
            img2 = null;
        }
    }

    public void EuDistanceBrench(DistanceState state){
        state.distance.distance(state.img1, state.img2);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHFeatureExtraction.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
