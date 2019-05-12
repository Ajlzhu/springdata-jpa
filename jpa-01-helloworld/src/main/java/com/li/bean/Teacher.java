package com.li.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 14:52
 */

@Data
@Table
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;

    private String tname;

    @Transient
    private Integer gid;

    /**
     * @OneToOne：一对一
     * @JoinColumn： 关联的外键
     *  name：外键对应实体类的属性名（这里teacher表中外键为gid，Teacher外键属性为gid）
     *  unique：
     */
    @OneToOne
    @JoinColumn(name = "gid",unique = true)
    private Grade grade;

    @Override
    public String toString() {
        return "Teacher{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", gid=" + gid +
                '}';
    }
}
