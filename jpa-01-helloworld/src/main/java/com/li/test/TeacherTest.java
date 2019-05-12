package com.li.test;

import com.li.bean.Grade;
import com.li.bean.Student;
import com.li.bean.Teacher;
import com.li.util.JpaUtil;
import org.junit.Test;

import javax.persistence.EntityManager;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 14:53
 */
public class TeacherTest {

    @Test
    public void add(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Teacher teacher = new Teacher();
        teacher.setTname("杜老师");
        teacher.setGid(1);
        entityManager.persist(teacher);
        JpaUtil.close(entityManager);
    }

    /**
     *  一对一
     *      根据班主任ID查询老师信息
     *      再查出班主任管理的班级
     * @return void
     */
    @Test
    public void find() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        Teacher teacher = entityManager.find(Teacher.class, 1);
        System.out.println(teacher);
        // 通过班主任信息获得班级信息
        System.out.println(teacher.getGrade());
        // 通过班级信息获得学生信息
        teacher.getGrade().getStudents().forEach(System.out::println);
        JpaUtil.close(entityManager);
    }
}
