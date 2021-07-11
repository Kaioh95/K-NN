package com.app;


import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class KNN {
    private final SortedMap<Double, List<String>> neighbours;

    public KNN() {
        this.neighbours = new TreeMap<>();
    }

    public void knnExec(List<ImageData> allImages, Byte[] imageX, int k){
        Distance similarityMeasure;
        similarityMeasure = new EuclideanDistance();

        Double distanceMeasure;
        for(ImageData img: allImages){
            distanceMeasure = similarityMeasure.distance(img.getFeatures(), imageX);
            if (neighbours.size() < k) {
                neighbours.put(distanceMeasure, img.getInfo());
            } else {
                if (neighbours.lastKey() > distanceMeasure) {
                    neighbours.remove(neighbours.lastKey());
                    neighbours.put(distanceMeasure, img.getInfo());
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
            predictAge += Double.parseDouble(nFeatures.get(1))/12;
            this.neighbours.remove(neighbours.lastKey());
        }

        return predictAge / k;
    }

}
