package com.app;

public class EuclideanDistance implements Distance {
    public EuclideanDistance(){}

    public double distance(Byte[] img, Byte[] imgX){
        double distance = 0;
        for(int i = 0; i < imgX.length; i++){
            distance += Math.pow(imgX[i] - img[i], 2);
        }
        return Math.sqrt(distance);
    }
}
