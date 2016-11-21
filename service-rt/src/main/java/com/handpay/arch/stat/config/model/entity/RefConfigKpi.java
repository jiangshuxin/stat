package com.handpay.arch.stat.config.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by fczheng on 2016/11/9.
 */
@Entity(name = "ref_config_kpi")
@IdClass(RefConfigKpi.class)
public class RefConfigKpi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @Column(name = "config_id")
    private int configId;
    @Id @Column(name = "kpi_short_name")
    private String kpiShortName;

    public RefConfigKpi(){}
    public RefConfigKpi(int configId, String kpiShortName){
        this.configId =configId;
        this.kpiShortName = kpiShortName;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getKpiShortName() {
        return kpiShortName;
    }

    public void setKpiShortName(String kpiShortName) {
        this.kpiShortName = kpiShortName;
    }
}
