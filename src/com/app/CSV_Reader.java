package com.app;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSV_Reader {
    //public String csv_path;
    public List<String[]> dataset;

    public CSV_Reader(){
        this.dataset = new ArrayList<>();
    }

    public void readData(String file,
                         String delimiter,
                         int skipLines) {
        this.dataset = new ArrayList<>();

        try{
            File myFile = new File(file);
            Scanner myReader = new Scanner(myFile);

            while(myReader.hasNextLine()){
                if(skipLines > 0){
                    skipLines--;
                    myReader.nextLine();
                    continue;
                }
                String [] line = myReader.nextLine().split(delimiter);
                dataset.add(line);
            }
            myReader.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void featuresToCSV(String src, String dest, boolean withHeader) {
        FeatureExtraction features = new FeatureExtraction();
        List<Float> imgFeatures;

        try {
            FileWriter arq = new FileWriter(dest, true);
            PrintWriter arqWrite = new PrintWriter(arq);

            for (int i = 0; i < this.dataset.size(); i++) {
                System.out.println(i);
                if(i == 0 && withHeader){
                    arqWrite.append(String.join(";", this.dataset.get(i)) + ";");
                    for (int attr = 1; attr <= 1000; attr++) {
                        if (attr < 1000)
                            arqWrite.append("attr" + attr + ";");
                        else
                            arqWrite.append("attr" + attr + "\n");
                    }
                }
                else{
                    arqWrite.append(String.join(";", this.dataset.get(i)) + ";");
                    imgFeatures = features.extract(src + this.dataset.get(i)[0] + ".png");

                    for (int j = 0; j < imgFeatures.size(); j++) {
                        if (j < imgFeatures.size() - 1)
                            arqWrite.append(imgFeatures.get(j) + ";");
                        else
                            arqWrite.append(imgFeatures.get(j) + "\n");
                    }
                }

            }
            arqWrite.close();

            /*if(withHeader){
                arqWrite.append("id;");
                for (int i = 1; i <= 1000; i++) {
                    if (i < 1000)
                        arqWrite.append("attr" + i + ";");
                    else
                        arqWrite.append("attr" + i + "\n");
                }
            }
            else {
                imgFeatures = features.extract(src);

                arqWrite.append(src.substring(90, 95) + ";");
                for (int j = 0; j < imgFeatures.size(); j++) {
                    if (j < imgFeatures.size() - 1)
                        arqWrite.append(imgFeatures.get(j) + ";");
                    else
                        arqWrite.append(imgFeatures.get(j) + "\n");
                }

            }*/

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }


}
