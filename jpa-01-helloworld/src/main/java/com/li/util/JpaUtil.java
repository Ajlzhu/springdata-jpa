package com.li.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author licheng
 * @description jap工具类
 * @create 2019/5/12 13:14
 */
public class JpaUtil {
    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityTransaction transaction = null;
    static {
        String persistenceUnitName = "hello-jpa";
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    /**
     * 获取EntityManager对象
     * @return javax.persistence.EntityManager
     */        
    public static EntityManager getEntityManager(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 事务支持
        transaction = entityManager.getTransaction();
        // 开启事务
        transaction.begin();
        return entityManager;
    }

    /**
     * 关闭
     * @return void
     */
    public static void close(EntityManager entityManager){
        // 提交事务
        transaction.commit();
        entityManager.close();
    }
}
