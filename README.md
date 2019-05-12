## 二、jpa常用注解

1. @Entity：注解标注于类上，用于指出该java类为实体类，将映射到指定的数据库表
2. @Table：与@Entity注解并列使用，用于实体类与数据库表的绑定
3. @Id：用于声明当前实体类的属性映射为数据表的主键
4. @GeneratedValue：指定主键的生成策略
5. @Basic：java实体类与数据表字段的映射（默认每个属性上都有添加该注解）
6. @Column：当实体类属性与表中字段名不一致时，可以使用该注解指定映射表的字段
7. @Transient：用于声明当前属性不与数据表字段做映射（即非数据表中的字段）

## 三、jpa常用api

### 3.1 Persistence

Persistence类常用于获取EntityManagerFactory的实例

- 常用方法

  ~~~java
  EntityManagerFactory createEntityManagerFactory(String persistenceUnitName)
      
  EntityManagerFactory createEntityManagerFactory(String persistenceUnitName, Map properties)   
  ~~~

### 3.2 EntityManagerFactory

EntityManagerFactory常用于获取EntityManager的实例

- 常用方法

  ~~~java
  EntityManager createEntityManager();
  ~~~

### 3.3 EntityManager

EntityManager常用于创建查询实例和获取事务管理实例

- 常用方法

  ~~~java
  Query createQuery(String var1);
  Query createNativeQuery(String var1);
  Query createNamedQuery(String var1);
  
  EntityTransaction getTransaction();
  ~~~

  

## 四、jpa关系映射

### 4.1 多对一

一对多关系中，必须存在一个关系维护端。在JPA规范中，要求many的一方作为关系的维护端，one的一方作为被维护端。在many方指定@ManyToOne注解，并使用@JoinColumn指定外键名称。

- 学生和班级的多对一映射关系

  ~~~java
  @Data
  @Table
  @Entity
  public class Student {
  
      @Id
      private String sid;
      private String sname;
      @Transient
      private Integer gid;
  	
      
      /**
       * @ManyToOne:多对一
       *  targetEntity：多对一中一方的实体类
       *  fetch：加载策略
       *      FetchType.LAZY 延迟加载
       *      FetchType.EAGER 立即加载
       * @JoinColumn：关联的外键
       *      name：外键对应实体类的属性名（这里student表中外键为gid，Student外键属性为gid）
       * @return
       */
      @ManyToOne(targetEntity = Grade.class,fetch = FetchType.LAZY)
      @JoinColumn(name="gid")
      private Grade grade;
      private Grade grade;
  }
  ~~~

### 4.2 一对多

可以在one方指定@OneToMany注解并设置mappedBy属性。以指定它是这一关联中的被维护端，many为维护端

- 班级和学生的一对多映射关系

  ~~~java
  @Data
  @Table
  @Entity
  public class Grade {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int id;
      private String gname;
  
      /**
       * @OneToMany：一对多
       *  mappedBy：一对多中，一方在多方中的属性名（这里指Student类中的grade属性名）
       * @return
       */
      @OneToMany(mappedBy = "grade")
      private List<Student> students;
  }
  ~~~

### 4.3 双向一对一

基于外键的一对一关联关系：在双向的一对一关联中，需要在关系被维护端中的@OneToOne注解中指定mappedBy属性，以指定是这一关联关系中的被维护端。同时需要在关系维护端建立外键列指向关系被维护端的主键列

- 班主任和班级的一对一关联关系

  ~~~java
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
  	
      @OneToOne
      @JoinColumn(name = "gid",unique = true)
      private Grade grade;
  }
  =================================华丽丽的分割线=====================================
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
  ~~~

### 4.4 双向多对多

多对多关系，我们先确定谁是多对多关系的维护端和被维护端。多对多关系的维护端和被维护端可以自行定义即可，这里我们将users定为维护端

- 用户和角色多对多关联关系

  ~~~java
  @Data
  @Table
  @Entity
  public class Users {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer id;
  
      private String uname;
  
      private String upass;
  
      private String name;
  
      private Integer age;
  
      private String remark;
  
      private Date createDate;
  
      /**
       *  @ManyToMany: 多对多
       *  @JoinTable：关联的中间表
       *      name：中间表名称
       *      joinColumns：当前表与中间表的关联关系
       *          @JoinColumn：当前表与中间表的关联列
       *              name：当前表与中间表关联的列名
       *              referencedColumnName：当前表的主键名
       *      inverseJoinColumns：另一表与中间表的关联关系
       *          @JoinColumn：另一表与中间表的关联列
       *              name：另一表与中间表关联的列名
       *              referencedColumnName：另一表的主键名
       */
      @ManyToMany
      @JoinTable(name = "user_role",
              joinColumns = {@JoinColumn(name="uid",referencedColumnName = "id")},
              inverseJoinColumns = {@JoinColumn(name = "rid",referencedColumnName = "id")}
      )
      private Set<Role> roleSet;
  }
  
  ===================================华丽丽的分割线=====================================
  @Data
  @Table
  @Entity
  public class Role {
  
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer id;
  
      private String rname;
      
  	/**
       * @ManyToMany： 多对多关联关系
       *  mappedBy：被维护端指向维护端的属性名（Users类中的roleSet属性）
       */
      @ManyToMany(mappedBy = "roleSet")
      private List<Users> userList; 
  }
  ~~~

## 五、jpql

JPQL语言，即Java Persistence Query Language的简称。JPQL是一种和SQL非常类似的中间性和对象化的查询语言，它最终会被编译成针对不同底层数据库的SQL查询，从而屏蔽不同数据库的差异。

- SQL关键字大写

- 类名属性名区分大小写

### 5.1 Query接口

Query接口封装了执行数据库查询的相关方法，可通过EntityManager的`createQuery`、`createNamedQuery`、

`createNativeQuery`来获得查询对象，进而可以调用Query接口的相关方法来执行查询

- query接口的主要方法
  - executeUpdate：执行insert、update或delete操作
  - getSingleResult：获取单个查询结果
  - getResultList：获取多个查询结果

~~~java
=========================getResultList============================
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
=========================getSingleResult============================
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
=========================executeUpdate============================
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
~~~

### 5.2 jpql参数设置方式

- ~~? 问号占位~~（**下标从0开始**，过时）

  ~~~java
  String jpql = "UPDATE Users SET uname = ? WHERE id = ?";
  
  // 设置参数
  query.setParameter(0,"zhaou");
  query.setParameter(1,5);
  ~~~

- 问号+数字占位

  ~~~java
  String jpql = "UPDATE Users SET uname = ?1 WHERE id = ?2";
  
  // 设置参数
  query.setParameter(1,"zhaou");
  query.setParameter(2,5);
  ~~~

- :参数名

  ~~~java
  String jpql = "FROM Users WHERE uname = :uname";
  
  // 设置参数
  query.setParameter("uname","lisi");
  ~~~

### 5.3 jpql查询部分字段信息

#### 5.3.1 object[]和List<object[]>

~~~java
// 单条结果为Object[],多条结果为List<Object[]>
String jpql = "SELECT uname, upass FROM Users WHERE id = ?1";
Query query = entityManager.createQuery(jpql);
query.setParameter(1,4);
Object[] result = (Object[]) query.getSingleResult();
System.out.println(result[0]);
System.out.println(result[1]);
~~~

#### 5.3.2 投影查询（将部分查询结果映射为对象）

前提：需要提供与查询字段个数和顺序保持一致的构造函数，同时必须提供无参构造方法

~~~java
// 构造函数
public Users(){}

public Users(String uname , String upass){
    this.uname = uname;
    this.upass = upass;
}

// 查询jpql
String jpql = "SELECT new Users(uname, upass) FROM Users WHERE id = :id";
~~~

### 5.4 本地查询(原生sql查询)

~~~java
String sql = "select * from users";
Query query = entityManager.createNativeQuery(sql, Users.class);
List list = query.getResultList();
list.forEach(System.out::println);
~~~

