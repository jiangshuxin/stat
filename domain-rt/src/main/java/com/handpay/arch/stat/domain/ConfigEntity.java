package com.handpay.arch.stat.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Created by fczheng on 2016/10/31.
 */
@Entity(name = "config_info")
public class ConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    protected int type;
    @Column(nullable = false, name="table_name")
    private String tableName;
    private String description;
    @Transient
    private String kpiNames;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getTableName() {
        return tableName;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKpiNames() {
        return kpiNames;
    }

    public void setKpiNames(String kpiNames) {
        this.kpiNames = kpiNames;
    }
}
