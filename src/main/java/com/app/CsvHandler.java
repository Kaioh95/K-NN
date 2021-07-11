package com.app;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@Getter
@Setter
public class CsvHandler {
    private String csvPath;
    private String imgsPath;
    private Map<Integer, ImageData> dataset = new HashMap<>();

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
                List<String> line = new ArrayList<>(
                        Arrays.asList(myReader.nextLine().split(delimiter)));
                this.dataset.put(Integer.parseInt(line.get(0)), new ImageData(line));
                System.out.println(this.dataset.get( Integer.parseInt(line.get(0)) ));
            }
            myReader.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
