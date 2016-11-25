package com.handpay.arch.stat.config.model.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
    @Column(nullable = false, name="maintain_man")
    private String maintainMan;
    @Column(nullable = false, name="maintain_date")
    private Date maintainDate;
    private String description;
    @Column(nullable = false, name="business_line")
    private String businessLine;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getMaintainMan() {
        return maintainMan;
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

    public void setMaintainMan(String maintainMan) {
        this.maintainMan = maintainMan;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }
}
