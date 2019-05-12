package com.li.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 14:00
 */
@Data
@Table
@Entity
public class Student {

    @Id
    private String sid;
    private String sname;
    @Transient
    private Integer gid;

    /**
     * @ManyToOne:多对一
     *  targetEntity：多对一中一方的实体类
     *  fetch：加载策略
     *      FetchType.LAZY 延迟加载
     *      FetchType.EAGER 立即加载
     * @JoinColumn：关联的外键
     *      name：外键对应实体类的属性名（这里student表中外键为gid，Student外键属性为gid）
     * @return
     */
    @ManyToOne(targetEntity = Grade.class,fetch = FetchType.LAZY)
    @JoinColumn(name="gid")
    private Grade grade;

    @Override
    public String toString() {
        return "Student{" +
                "sid='" + sid + '\'' +
                ", sname='" + sname + '\'' +
                ", gid=" + gid +
                '}';
    }
}
