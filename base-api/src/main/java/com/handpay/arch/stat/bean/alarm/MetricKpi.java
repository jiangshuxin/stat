package com.handpay.arch.stat.domain.dto;

import java.io.Serializable;

/**
 * Created by fczheng on 2016/11/17.
 */
public class MetricKpi implements Serializable {
    private static final long serialVersionUID = 1L;

    public MetricKpi() {
    }
    public MetricKpi(String shortName) {
        this.shortName = shortName;
    }

    private String shortName;
    private String englishName;
    private String displayName;
    private String description;
    private String unit;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
