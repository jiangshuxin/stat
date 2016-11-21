package com.handpay.arch.stat.config.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fczheng on 2016/11/7.
 */

@Entity(name = "metric_kpi")
public class MetricKpiEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public MetricKpiEntity() {
    }
    public MetricKpiEntity(String shortName) {
        this.shortName = shortName;
    }

    @Id @Column(name = "short_name")
    private String shortName;
    @Column(name = "english_name")
    private String englishName;
    @Column(name = "display_name")
    private String displayName;
    private String description;
    private String unit;

    public String getShortName() {
        return shortName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
