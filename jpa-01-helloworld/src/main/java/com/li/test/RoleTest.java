package com.li.test;

import com.li.bean.Role;
import com.li.bean.Users;
import com.li.util.JpaUtil;
import org.junit.Test;

import javax.persistence.EntityManager;

/**
 * @author licheng
 * @description
 * @create 2019/5/12 15:48
 */
public class RoleTest {

    /**
     * 角色和用户多对多关系测试
     */
    @Test
    public void find(){
        EntityManager entityManager = JpaUtil.getEntityManager();
        Role role = entityManager.find(Role.class, 1);
        System.out.println(role);

        // 查看哪些人是管理员
        role.getUserList().forEach(System.out::println);
        JpaUtil.close(entityManager);
    }
}
