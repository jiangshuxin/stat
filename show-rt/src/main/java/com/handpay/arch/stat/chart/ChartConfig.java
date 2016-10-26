package com.handpay.arch.stat.chart;

import java.io.Serializable;

/**
 * Created by sxjiang on 2016/10/26.
 */
public class ChartConfig implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private ChartType type;
    private String url;
    private Boolean saved;
    private Long interval;
    private Object source;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChartType getType() {
        return type;
    }

    public void setType(ChartType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }
}
