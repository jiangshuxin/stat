package com.handpay.arch.stat.chart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sxjiang on 2016/10/26.
 */
public class LineArea implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String title;
    private List<String> xAxisDataList;
    private List<Series> seriesList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getxAxisDataList() {
        return xAxisDataList;
    }

    public void setxAxisDataList(List<String> xAxisDataList) {
        this.xAxisDataList = xAxisDataList;
    }

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public static class Series{
        private String name;
        private List<String> dataList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getDataList() {
            return dataList;
        }

        public void setDataList(List<String> dataList) {
            this.dataList = dataList;
        }
    }
}
