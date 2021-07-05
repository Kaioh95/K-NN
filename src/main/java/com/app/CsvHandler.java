package com.app;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Getter
@Setter
public class CsvHandler {
    private String csvPath;
    private String imgsPath;
    private Map<String, List<String>> dataset = new HashMap<>();

    public CsvHandler(String csvPath, String imgsPath){
        this.csvPath = csvPath;
        this.imgsPath = imgsPath;
    }

    public void readCsv(String delimiter, int skipLines) {

        try{
            File myFile = new File(this.csvPath);
            Scanner myReader = new Scanner(myFile);

            while(myReader.hasNextLine()){
                if(skipLines > 0){
                    skipLines--;
                    myReader.nextLine();
                    continue;
                }
                List<String> line = new ArrayList<String>(
                        Arrays.asList(myReader.nextLine().split(delimiter)));
                this.dataset.put(line.get(0), line.subList(1, line.size()));
                System.out.println(this.dataset.get(line.get(0)));
            }
            myReader.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void allImagesFeatures() {
        FeatureExtraction features = new FeatureExtraction();
        List<Float> imgFeatures;

        File file = new File(this.imgsPath);
        for(File img: Objects.requireNonNull(file.listFiles())){
            String imgKey = Arrays.asList(img.getName().split("\\.")).get(0);
            System.out.println(imgKey);

            imgFeatures = features.extract(this.imgsPath + imgKey + ".png");

            for (Float imgFeature : imgFeatures) {
                this.dataset.get(imgKey).add(String.valueOf(imgFeature));
            }
        }

    }


};
