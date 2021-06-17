package com.app;

import java.util.*;

public class KNN {
    public SortedMap<Double, String[]> neighbours;

    public KNN() {
        this.neighbours = new TreeMap<>();
    }

    public void k_nn_exec(List<String[]> allImages, List<Float> imageX, int k, int option){
        Distance similarityMeasure;
        similarityMeasure = new EuclideanDistance();

        Double distanceMeasure;
        for(String[] img: allImages){
            String[] subFromImg = Arrays.copyOfRange(img, 3, img.length);
            distanceMeasure = similarityMeasure.distance(subFromImg, imageX);
            if (neighbours.size() < k) {
                neighbours.put(distanceMeasure, img);
            } else {
                if (neighbours.lastKey() > distanceMeasure) {
                    neighbours.remove(neighbours.lastKey());
                    neighbours.put(distanceMeasure, img);
                }
            }
        }

    }

    public double predict(){
        double predictAge = 0;
        String[] nFeatures;
        int k = this.neighbours.size();

        while(!this.neighbours.isEmpty()){
            nFeatures = this.neighbours.get(neighbours.lastKey());
            predictAge += Double.parseDouble(nFeatures[1])/12;
            this.neighbours.remove(neighbours.lastKey());
        }

        return predictAge / k;
    }

}
