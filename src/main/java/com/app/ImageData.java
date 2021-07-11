package com.app;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImageData {
    private List<String> info;
    private Byte[] features;

    public ImageData(List<String> info){
        this.info = info;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "info=" + info +
                '}';
    }

    public String toCsvWrite(){
        return String.join(";", info);
    }
}
