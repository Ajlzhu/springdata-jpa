package com.li.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 15:31
 */

@Data
@Table
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rname;

    /**
     * @ManyToMany： 多对多关联关系
     *  mappedBy：被维护端指向维护端的属性名（Users类中的roleSet属性）
     */
    @ManyToMany(mappedBy = "roleSet")
    private List<Users> userList;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rname='" + rname + '\'' +
                '}';
    }
}
