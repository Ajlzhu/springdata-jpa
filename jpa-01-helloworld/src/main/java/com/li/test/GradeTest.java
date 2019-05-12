package com.li.test;

import com.li.bean.Grade;
import com.li.bean.Student;
import com.li.util.JpaUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 14:02
 */
public class GradeTest {

    @Test
    public void add() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        Grade grade = new Grade();
        grade.setGname("java");
        entityManager.persist(grade);
        JpaUtil.close(entityManager);
    }

    /**
     * 根据班级ID，查询班级信息
     * 再查询到所关联的所有学生信息
     * @param
     * @return void
     */
    @Test
    public void find(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Grade grade = entityManager.find(Grade.class, 1);

        System.out.println(grade);
        // 获取班主任信息OneToOne
        System.out.println(grade.getTeacher());

        // 获取班级学生信息OneToMany
        List<Student> students = grade.getStudents();
        students.forEach(System.out::println);
        JpaUtil.close(entityManager);
    }
}
