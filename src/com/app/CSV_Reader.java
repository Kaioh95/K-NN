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
            PrintWriter arqWriteM = new PrintWriter(new FileWriter(dest+"M.csv", true));
            PrintWriter arqWriteF = new PrintWriter(new FileWriter(dest+"F.csv", true));

            if(withHeader){
                arqWriteM.append(String.join(";", this.dataset.get(0)) + ";");
                arqWriteF.append(String.join(";", this.dataset.get(0)) + ";");
                for (int attr = 1; attr <= 1000; attr++) {
                    if (attr < 1000) {
                        arqWriteM.append("attr" + attr + ";");
                        arqWriteF.append("attr" + attr + ";");
                    }
                    else {
                        arqWriteM.append("attr" + attr + "\n");
                        arqWriteF.append("attr" + attr + "\n");
                    }
                }
            }
            for (int i = 1; i < this.dataset.size(); i++) {
                System.out.println(i);
                if(this.dataset.get(i)[2].equals("True"))
                    arqWriteM.append(String.join(";", this.dataset.get(i)) + ";");
                else
                    arqWriteF.append(String.join(";", this.dataset.get(i)) + ";");

                imgFeatures = features.extract(src + this.dataset.get(i)[0] + ".png");

                for (int j = 0; j < imgFeatures.size(); j++) {
                    if (j < imgFeatures.size() - 1) {
                        if(this.dataset.get(i)[2].equals("True"))
                            arqWriteM.append(imgFeatures.get(j) + ";");
                        else
                            arqWriteF.append(imgFeatures.get(j) + ";");
                    }
                    else {
                        if(this.dataset.get(i)[2].equals("True"))
                            arqWriteM.append(imgFeatures.get(j) + "\n");
                        else
                            arqWriteF.append(imgFeatures.get(j) + "\n");
                    }
                }

            }
            arqWriteM.close();
            arqWriteF.close();

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

    }


}
