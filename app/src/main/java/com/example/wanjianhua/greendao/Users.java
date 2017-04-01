package com.example.wanjianhua.greendao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by wanjianhua on 2017/3/22.
 */
@Entity
public class Users {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    @Transient
    private int tempUsageCount;
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1253555609)
    public Users(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 2146996206)
    public Users() {
    }

   
}
