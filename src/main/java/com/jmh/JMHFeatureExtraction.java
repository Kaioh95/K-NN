package com.jmh;

import com.app.FeatureExtraction;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
/*import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;*/

public class JMHFeatureExtraction {

    @State(Scope.Thread)
    public static class FtState{
        FeatureExtraction ft = new FeatureExtraction();
    }

    @Benchmark
    public void featureBrench(FtState state) {
        state.ft.extract("/home/kaio/IdeaProjects/boneage_dataset/boneage-training-dataset/boneage-training-dataset/5471.png");
    }


    /*
     * ============================== HOW TO RUN THIS TEST: ====================================
     *
     * You can see measureRight() yields the result, and measureWrong() fires
     * the assert at the end of the run.
     *
     * You can run this test:
     *
     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -ea -jar target/benchmarks.jar JMHSample_05 -f 1
     *    (we requested single fork; there are also other options, see -h)
     *
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHFeatureExtraction.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
