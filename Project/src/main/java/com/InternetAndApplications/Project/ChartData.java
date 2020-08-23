package com.InternetAndApplications.Project;

import java.util.ArrayList;

public class ChartData {
    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        this.labels = labels;
    }

    public ArrayList<DataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(ArrayList<DataSet> datasets) {
        this.datasets = datasets;
    }

    private ArrayList<String> labels;

    private ArrayList<DataSet> datasets;

    public ChartData(ArrayList<String> labels, ArrayList<DataSet> datasets) {
        this.labels = labels;
        this.datasets = datasets;
    }
}
