package com.flightRecorder;

import com.app.Main;
import lombok.SneakyThrows;

public class MainFlightRecorder {
    public static void main(String[] args) throws Exception{
        new Thread(new KnnFRThread()).start();
    }
}

class KnnFRThread implements Runnable{
    Main knnMain = new Main();

    @SneakyThrows
    @Override
    public void run() {
        knnMain.main(null);
    }
}
