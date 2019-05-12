package com.li.test;

import com.li.bean.User;
import com.li.bean.Users;
import com.li.util.JpaUtil;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * @author licheng
 * @description 本地查询测试 原生SQL
 * @create 2019/5/12 17:02
 */
public class NativeQueryTest {

    @Test
    public void t1(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String sql = "select * from users";
        Query query = entityManager.createNativeQuery(sql, Users.class);
        List list = query.getResultList();
        list.forEach(System.out::println);

        JpaUtil.close(entityManager);
    }

    /**
     *查询部分字段并封装成VO返回页面
     */
    @Test
    public void t2(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        String sql = "SELECT\n" +
                "	u.name,\n" +
                "	u.age,\n" +
                "	GROUP_CONCAT( r.rname )  roles\n" +
                "FROM\n" +
                "	users u,\n" +
                "	role r,\n" +
                "	user_role ur \n" +
                "WHERE\n" +
                "	u.id = ur.uid \n" +
                "	AND ur.rid = r.id \n" +
                "GROUP BY\n" +
                "	u.id";
        Query query = entityManager.createNativeQuery(sql);
        List<User> list = query.unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.aliasToBean(User.class))
                .list();

        list.forEach(System.out::println);
        JpaUtil.close(entityManager);
    }
}
