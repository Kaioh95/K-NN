package com.app;

import java.util.List;

public class EuclideanDistance implements Distance {
    public EuclideanDistance(){}

    public double distance(List<String> img, List<Float> imgX){
        double distance = 0;
        for(int i = 0; i < imgX.size(); i++){
            double xi = imgX.get(i).doubleValue();
            double yi = Double.parseDouble(img.get(i));
            distance += Math.pow(xi - yi, 2);
        }
        return Math.sqrt(distance);
    }
}
