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
    private int type;
    @Column(nullable = false, name="table_name")
    private String tableName;
    private String description;
    @OneToMany(fetch= FetchType.EAGER)
    @JoinTable(name = "ref_config_kpi",
            joinColumns = @JoinColumn(name="config_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name="kpi_id", referencedColumnName="id")
    )
    private List<MetricKpi> kpiList;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return TYPE.findName(type);
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

    public List<MetricKpi> getKpiList() {
        return kpiList;
    }
    public void setKpiList(List<MetricKpi> kpiList) {
        this.kpiList = kpiList;
    }

    public enum TYPE {
        SERVER(10001, "服务器监控"),
        RPC(10002, "RPC信息"),
        JVM(10003, "JVM"),
        BUSINESS(10004, "业务数据");


        private int id;
        private String showName;
        private TYPE(int id, String showName) {
            this.id = id;
            this.showName = showName;
        }

        public static String findName(int tid) {
            for (TYPE type : TYPE.values()) {
                if (type.id == tid) {
                    return type.showName;
                }
            }
            throw new IllegalArgumentException("此配置类型不存在");
        }
    }
}
