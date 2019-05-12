package com.li.test;

import com.li.bean.Grade;
import com.li.bean.Student;
import com.li.util.JpaUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.UUID;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 14:07
 */
public class StudentTest {

    @Test
    public void add(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Student student = new Student();
        student.setSid(UUID.randomUUID().toString());
        student.setSname("李四");
        student.setGid(1);
        entityManager.persist(student);
        JpaUtil.close(entityManager);
    }
    /**
     *  根基学生id查询学生信息
     *  再查询到对应的班级信息
     * @return void
     */
    @Test
    public void find(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Student student = entityManager.find(Student.class, "77a12bf1-6bca-4413-9a9b-305d0ea5188f");
        System.out.println(student);
        System.out.println(student.getGrade());
        JpaUtil.close(entityManager);
    }
}
