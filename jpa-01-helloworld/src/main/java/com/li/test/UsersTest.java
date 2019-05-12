package com.li.test;

import com.li.bean.Users;
import com.li.util.JpaUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 11:07
 */
public class UsersTest {

    @Test
    public void add(){
        // 1.得到EntitymanagerFactory对象
        String persistenceUnitName = "hello-jpa";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        // 2.得到EntityManager对象
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // 3.事务的支持
        EntityTransaction transaction = entityManager.getTransaction();
        // 开启事务
        transaction.begin();
        // 4.业务逻辑(添加用户)
        Users u = new Users();
        u.setName("杰克");
        u.setUname("jack");
        u.setUpass("123456");
        u.setAge(20);
        u.setCreateDate(new Date());
        u.setRemark("第一个jpa代码-执行添加功能");

        // 执行保存操作
        entityManager.persist(u);

        // 5.事务的提交
        transaction.commit();
        // 6.关闭EntityMananer
        entityManager.close();
        // 7.关闭EntitymanagerFactory
        entityManagerFactory.close();
    }

    @Test
    public void get(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Users users = entityManager.find(Users.class, 3);
        System.out.println(users);
        JpaUtil.close(entityManager);
    }
    /**
     * jpa对象的三种状态
     *  瞬时态：新创建的对象，没有主键
     *  持久态：
     *  游离态：
     * @return void
     */
    @Test
    public void addUsers(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        // 瞬时态对象（新建状态,没有主键）
        Users users = new Users();
        users.setName("张三");

        //持久态对象(持久化操作，但事务还未提交)
        entityManager.persist(users);
        JpaUtil.close(entityManager);

        //游离态对象(事务已提交或EntityManager关闭时)
        System.out.println(users);
    }

    /**
     * Hibernate get和load方法的区别
     *  都是根据主键id查询单个实体结果
     *  get立即加载
     *  get查询ID不存在时返回NULL
     *  load支持延迟加载
     *  load查询ID不存在时抛出ObjectNotFoundException
     *
     * JPA中find和getReference的区别
     *  都是根据主键id查询单个实体结果
     *  get立即加载
     *  get查询ID不存在时返回NULL
     *  load支持延迟加载
     *  load查询ID不存在时抛出EntityNotFoundException
     * @return void
     */
    @Test
    public void find(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Users users = entityManager.find(Users.class, 5);
        System.out.println(users);

        // 获取用户所具有的角色信息
        users.getRoleSet().forEach(System.out::println);
        JpaUtil.close(entityManager);
    }
    /**
     *
     * @return void
     */
    @Test
    public void getReference(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Users users = entityManager.getReference(Users.class, 1);
        System.out.println(users);
        JpaUtil.close(entityManager);
    }

    /**
     * remove只能删除持久化对象，不能删除游离态对象
     * @return void
     */
    @Test
    public void remove(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Users users = entityManager.find(Users.class, 3);
        entityManager.remove(users);
        System.out.println(users);
        JpaUtil.close(entityManager);
    }

    /**
     * merge方法：
     *  主键ID为空则执行insert操作
     *  主键ID不为空执行
     *      根据id查询数据：
     *          若数据为空则执行insert操作
     *          若数据不为空则执行update操作
     *
     * @return void
     */
    @Test
    public void merge(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Users u = new Users();
        u.setId(5);
        u.setName("zhaowu");
        Users users = entityManager.merge(u);
        System.out.println(users);
        JpaUtil.close(entityManager);
    }
}
