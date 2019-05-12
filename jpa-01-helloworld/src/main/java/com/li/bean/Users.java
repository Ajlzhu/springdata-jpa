package com.li.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 11:54
 */
@Data
@Table
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uname;

    private String upass;

    private String name;

    private Integer age;

    private String remark;

    private Date createDate;

    public Users(){}

    public Users(String uname , String upass){
        this.uname = uname;
        this.upass = upass;
    }
    /**
     *  @ManyToMany: 多对多
     *  @JoinTable：关联的中间表
     *      name：中间表名称
     *      joinColumns：当前表与中间表的关联关系
     *          @JoinColumn：当前表与中间表的关联列
     *              name：当前表与中间表关联的列名
     *              referencedColumnName：当前表的主键名
     *      inverseJoinColumns：另一表与中间表的关联关系
     *          @JoinColumn：另一表与中间表的关联列
     *              name：另一表与中间表关联的列名
     *              referencedColumnName：另一表的主键名
     */
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name="uid",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rid",referencedColumnName = "id")}
    )
    private Set<Role> roleSet;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", upass='" + upass + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
