package com.li.test;

import com.li.bean.Users;
import com.li.util.JpaUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 15:59
 */
public class JPQLTest {

    /**
     * 查询所有用户信息
     */
    @Test
    public void t1(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT u FROM Users u ORDER BY age desc";
        TypedQuery<Users> query = entityManager.createQuery(jpql, Users.class);
        List<Users> usersList = query.getResultList();
        for (int i = 0; i < usersList.size(); i++) {
            System.out.println(usersList.get(i));
        }
        JpaUtil.close(entityManager);
    }
    /**
     * 查询总记录数
     */
    @Test
    public void t2(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT COUNT(id) FROM Users";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        Long result = query.getSingleResult();
        System.out.println("总记录数："+ result);
        JpaUtil.close(entityManager);
    }
    /**
     * 更新记录
     */
    @Test
    public void t3(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "UPDATE Users SET uname = ?1 WHERE id = ?2";
        Query query = entityManager.createQuery(jpql);
        // 设置参数
        query.setParameter(1,"zhaou");
        query.setParameter(2,5);
        // 执行更新
        int i = query.executeUpdate();
        System.out.println("影响的行数" + i);
        JpaUtil.close(entityManager);
    }
    @Test
    public void t4(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "FROM Users WHERE uname = :uname";
        TypedQuery<Users> query = entityManager.createQuery(jpql, Users.class);
        // 设置参数
        query.setParameter("uname","lisi");
        // 执行查询
        Users users = query.getSingleResult();
        System.out.println(users);
        JpaUtil.close(entityManager);
    }
    /**
     *jpql查询部分字段
     */
    @Test
    public void t5(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT uname, upass FROM Users WHERE id = ?1";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,4);
        Object[] result = (Object[]) query.getSingleResult();
        System.out.println(result[0]);
        System.out.println(result[1]);

        JpaUtil.close(entityManager);
    }
    /**
     *jpql查询部分字段:投影查询
     */
    @Test
    public void t6(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String jpql = "SELECT new Users(uname, upass) FROM Users WHERE id = :id";
        TypedQuery<Users> query = entityManager.createQuery(jpql, Users.class);
        // 设置参数
        query.setParameter("id",4);
        // 执行查询
        Users users = query.getSingleResult();
        System.out.println(users);
        JpaUtil.close(entityManager);
    }


}
