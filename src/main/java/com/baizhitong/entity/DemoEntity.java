package com.baizhitong.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by wangsj on 2017/11/28.
 */
@Entity
@Table(name="test_master_slave")
public class DemoEntity implements Serializable{

    @Id
    @GeneratedValue
    public Integer id;

    @Column
    public String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
