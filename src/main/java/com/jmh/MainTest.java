package com.jmh;

import java.io.IOException;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class MainTest {

    public static void main(String[] args) throws RunnerException, IOException {
        Options opt = new OptionsBuilder()
                .include(JMHFeatureExtraction.class.getSimpleName())
                .threads(4)
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
