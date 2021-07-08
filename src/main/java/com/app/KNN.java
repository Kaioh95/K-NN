package com.app;


import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class KNN {
    private final SortedMap<Double, List<String>> neighbours;

    public KNN() {
        this.neighbours = new TreeMap<>();
    }

    public void knnExec(List<List<String>> allImages, List<Float> imageX, int k){
        Distance similarityMeasure;
        similarityMeasure = new EuclideanDistance();

        Double distanceMeasure;
        for(List<String> img: allImages){
            List<String> subFromImg = img.subList(2, img.size());
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
        List<String> nFeatures;
        int k = this.neighbours.size();

        while(!this.neighbours.isEmpty()){
            nFeatures = this.neighbours.get(neighbours.lastKey());
            predictAge += Double.parseDouble(nFeatures.get(0))/12;
            this.neighbours.remove(neighbours.lastKey());
        }

        return predictAge / k;
    }

}
