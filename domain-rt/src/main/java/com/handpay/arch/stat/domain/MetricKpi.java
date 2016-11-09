package com.handpay.arch.stat.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by fczheng on 2016/11/7.
 */

@Entity(name = "metric_kpi")
public class MetricKpi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private int id;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "english_name")
    private String englishName;
    @Column(name = "chinese_name")
    private String chineseName;
    private String unit;

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public String getUnit() {
        return unit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
