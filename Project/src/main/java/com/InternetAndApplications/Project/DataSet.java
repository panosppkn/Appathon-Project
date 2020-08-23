package com.InternetAndApplications.Project;

import java.util.ArrayList;
import java.util.Random;

public class DataSet {
    private String label;

    private ArrayList<Integer> data;

    private ArrayList<String> backgroundColor;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }

    public ArrayList<String> getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(ArrayList<String> backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public DataSet(String label, ArrayList<Integer> data, ArrayList<String> backgroundColor) {
        this.label = label;
        this.data = data;
        String str = "";

        //generate random backgroundColor for each data
        Random rand = new Random(); //instance of random class
        for(int i=0;i<data.size(); i++) {
            str = "rgba("+ rand.nextInt(256)
                    +", "+ rand.nextInt(256)
                    +", "+ rand.nextInt(256)
                    +", "+ rand.nextInt(256)
                    +")";
            //generate random values from 0-255

            backgroundColor.add(str);
        }
        this.backgroundColor = backgroundColor;
    }
}
