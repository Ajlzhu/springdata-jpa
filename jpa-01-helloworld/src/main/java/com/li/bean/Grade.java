package com.li.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 13:57
 */

@Data
@Table
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String gname;

    /**
     * @OneToMany：一对一
     *  mappedBy：一对一中，被维护端在维护端的属性名
     * @return
     */
    @OneToOne(mappedBy = "grade")
    private Teacher teacher;


    /**
     * @OneToMany：一对多
     *  mappedBy：一对多中，一方在多方中的属性名
     * @return
     */
    @OneToMany(mappedBy = "grade")
    private List<Student> students;

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", gname='" + gname + '\'' +
                '}';
    }
}
