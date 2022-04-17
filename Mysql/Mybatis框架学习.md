# MyBatis框架学习

## 代理模式

名词：目标对象、代理对象、客户端

例：生产厂商、小卖部、学生

房东、中介、租房人

使用情况：无法访问目标对象，通过代对象进行访问，而且增强式的访问。适合进行业务扩展的功能

功能：

- 控制目标对象的访问
- 增强功能

分类：

- 静态代理
- 动态代理(JDK动态代理和CGLIB动态代理'子类代理')

### 静态代理

特点：

- 目标对象和代理对象实现同一个业务接口
- 目标对象必须实现接口
- 目标对象在程序运行前就已经存在
- 能够灵活的进行目标对象的切换，却无法进行功能的灵活处理(使用动态代理解决此问题)

缺点：

- 业务改变，要进行大量代码修改，实现复杂
- 代理类是以.java的文件形式存在，在调用前就已经存在，比较死板

实现：

业务`接口`Service：

/service/Serice.java

~~~java
void sing();
~~~

目标对象：刘德华SuperStarLiu/周润发SuperStarZhou

/impl/SuperStarLiu.java

~~~java
// 实现业务接口Service
void sing(){
    // 刘德华唱歌
}
~~~

/impl/SuperStarZhou.java

```java
// 实现业务接口Service
void sing(){
    // 周润发唱歌
}
```



代理对象：明星助理Agent

/imp/Agent.java

```java
// 实现业务接口Service
public Service target;// 目标对象：刘德华/周润发
// 传入目标对象
public Agent(Service target){
    this.target = target;
}
void sing(){
    // 预定唱歌时间
    // 场地、费用...
    
    // 业务功能必须由目标对象完成
    // 让明星来唱歌
    // 面向接口编程：调用时，接口指向实现类
    target.sing();
}
```

客户端：学校

在测试`test`里完成，maven项目文件结构下/test/

/test/MyTest.java

```java
// 和助理谈
@Test
testAgent(){
    // 有接口和实现类，必须使用接口指向实现类(规范)
    Service agent = new Agent(new SuperStarLiu());// 叫刘德华来唱歌
    agent.sing();
    // 预定唱歌时间
    // 场地、费用...

    // 刘德华唱歌
}
```



面向接口编程(重要)

- 类中的成员变量设计为接口，方法的形参设计为接口，方法的返回值设计为接口，调用时接口指向实现类

### 动态代理

​	代理对象在程序运行的过程中动态在内存构建，可以灵活的进行业务功能的切换。

特点：

- 能进行业务功能的灵活处理

#### JDK动态代理(常用)

- 目标对象必须实现业务接口
- *JDK代理对象不需要实现业务接口* 
- JDK动态代理的对象在程序运行前不存在，在程序运行时动态的在内存中构建
- JDK动态代理灵活的进行业务功能的切换
- 本类中的方法(非接口中的方法)不能被代理

用到的类和接口

- `Proxy`类(java.lang.reflect.Proxy)：

  方法：`Proxy.newProxyInstance(......)`

  专门用来生成动态代理对象

  ~~~java
  public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h) throws IllegalArgumentException
  {...}
  // ClassLoader loader 类加载器。完成目标对象的加载，到内存中
  // Class<?>[] interfaces 泛型数组，任意类型，目标对象实现的所有对象，实现多接口(接口数组)。拿到接口中的所有方法
  // InvocationHandler h 类型似于Agent的功能，代理的功能和目标对象的业务调用的实现,实现代理功能的接口
  ~~~

  - Method类

    `反射`用的类，用来进行目标对象的方法的反射调用，相当于方法传参，method对象接住我们正在调用的方法sing()

    method == sing()等方法

    method.invoke(); ==>手工调用目标方法sing()

  - InvocationHandler接口

    它时实现代理和业务功能的，我们在调用时使用匿名内部类实现

    

    实现：

    > 业务接口：
    > /service/Serice.java
    > 目标对象：
    > /service/impl/SuperStarLiu.java
    > /service/impl/SuperStarZhou.java
    >
    > 代理工厂：
    > /proxy/ProxyFactory.java
    >
    > 客户端：
    >
    > /test/MyTest.java

    代理工厂：
    /proxy/ProxyFactory.java

    ~~~java
    Service target;
    // 传入目标对象
    // Service target 接口类型，限制传入类型为接口的实现类
    // Object obj 任何类的父类，不能限制传入类，需要判断
    public ProxyFactory(Service target){
        this.target = target;
    }
    // 返回动态代理对象
    public Object getAgent(){
        return Proxy.newProxyInstance(target.getClass.getClassLoader(),target.getClass.getInterfaces(),
    new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // Object proxy 创建代理传入
                    // Method method 目标方法
                    // Object[] args 目标方法的参数
                    // 代理功能
                      // 预定唱歌时间
       				 // 场地、费用...
                    // 目标功能(主业务功能实现)
                    // sing(),show()等功能调用，根据外面调用动态调用，但非接口的业务功能不被代理(不能增强功能)
                    Object object = method.invoke(target,args);
                    return object;// 目标方法的返回值
                }
            })；
    }
    ~~~

    getAgent();// 代理对象的返回值

    invoke(proxy, method,args);// 目标方法的返回值

    客户端：

    /test/MyTest.java

    ~~~java
    // 和助理谈
    @Test
    testJDK(){
        // 叫刘德华来唱歌，在代理工厂找刘德华的助理
        ProxayFactory factory = new ProxayFactory(new SuperStarLiu());
        // 获取动态代理对象对象
        Service agent = (Service)factory.getAgent();
        // 刘德华唱歌
        agent.sing();
        
        System.out.println(agent.getClass());
        // com.sun.proxy.$Proxy JDK动态代理类型
        Service service = new SuperStarLiu();
        System.out.println(service.getClass());
        // impl.SuperStarLiu 实现的类型
    }
    ~~~

    

增加Service接口的方法(增添业务)==>SuperStar实现方法(学习业务)

ProxyFactory代理工厂不需要修改(动态代理)

注意：*代理对象不需要实现接口，目标对象一定要实现接口*



#### CGLib动态代理

​	又称为子类，通过动态的在内存中构建子类对象，重写父类的方法进行代理功能的增强。

​	如果*目标对象没有实现接口*，则只能通过CGLib子类代理来进行功能增强。

​	子类代理是对象字节码框架ASM来实现的

使用：代理没有实现接口的类

特点：

- 引入`cglib-jar`文件，maven可以添加直接添加`spring-core-5.2.5.jar`依赖，spring的核心包已包含cglib功能，直接引用`spring-core-5.2.5.jar`
- 被代理的类不能为final，否则报错(无法被继承，无法被重写)
- 目标对象的方法如果为final/static，那么就不会被拦截，既不会执行目标对象额外的业务方法

简单实现：

> 目标对象：
> SuperStarLiu.java
> SuperStarZhou.java
>
> 代理对象(继承父类SuperStarLiu)：
> SubSuperStarLiu.java
>
> 客户端：
>
> /test/MyTest.java

SubSuperStarLiu.java

~~~java
sing(){
    // 子类完成代理功能
  		 // 预定唱歌时间
   		// 场地、费用...
    // 父类实现自己的业务功能
    super.sing();
}
~~~

客户端：

/test/MyTest.java

```java
@Test
testAgent(){
    SuperStarLiu liu = new SubSuperStarLiu();
    liu.sing();
}
```

实现：

- 

代理工厂：
/proxy/ProxyFactory.java(实现)

~~~java
Object target;
// 传入目标对象
// Service target 接口类型，限制传入类型为接口的实现类
// Object obj 任何类的父类，不能限制传入类，需要判断
public ProxyFactory(Object target){
    this.target = target;
}
// Cglib采用底层的字节码技术，在子类中采用方法拦截的技术，拦截父类指定方法的调用，并顺序植入代理功能的代码
// 增强代理功能，没有返回对象
Object intercept(......){
    // 代理对象的功能
    	// 预定唱歌时间
   		 // 场地、费用...
    Object returnValue = method.invoke(target,arg2)
        
    return returnValue;
}


// 生成代理对象
Object getProxyInstance(){
    // 使用工具类
    // 完成子类代理对象的创建
    Enhancer en = new Enhancer();
    // 设置父类
    en.setSuperclass(target.getClass());
    // 设置回调函数,调用intercept() ==>↑
    en.setCallback(this);
    // 返回子类代理对象
    return en.create();
   
}

}
~~~

​    Enhancer en = new Enhancer() ==>  en.create()

创建子类代理对象==>完成子类代理的对象的创建

## 面向对象的接口设计

- 类中的成员变量设计为接口
- 传入目标对象时，方法的参数设计为接口
- 方法的返回值设置为接口
- 接口指向实现类(多态)

# MyBatis框架

​	 简化JDBC繁琐的访问机制(加载驱动、获取连接、编写语句、执行语句、获取结果、关闭资源等)，让开发者专注SQL的处理。

结构图

MyBatis配置文件：

​	`SqlMapConfig.xml`(核心)

​	`Mapper1.xml，Mapper2.xml`(写SQL语句)

↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

SqlSessionFactory(工厂)==>SqlSession(Connection、Staterment)==>Executor(底层执行器)==> MapperStatement(SQL语句)==>数据库

输入映射==>MapperStatement==>输出映射

输入映射：

- Map
- String、Integer等基础数据类型
- Pojo

输出映射：

- Map
- String、Integer等基础数据类型
- Pojo
- List



## 三层架构

​	在项目开发中，遵循的一种形式模式。分三层

- 界面层(Controller)：用于接受客户端的输入，调用业务逻辑层进行功能处理，返回结果给客户端。比如servlet

  - UserController

    ```java
    UserService userService = new UserServiceImpl();
    // 规范，接口指向实现类
    ```

    

- 业务逻辑层(接口Service+实现类ServiceImpl)：用来进行整个项目的业务逻辑处理，向上为界面提供处理结果，向下问数据访问层要数据

  - UserService

  - UserServiceImpl

    ```java
    UsersDao uDao = new UsersDaoImpl();
    UsersDao uDao = ?new.UsersMapper.xml文件对象
    // 无法在业务逻辑层访问xml文件中的功能
        // 解决方案：动态代理
        
    // 在业务逻辑层依然使用接口调用xml文件中的功能，这个功能由动态代理对象代理出来
    ```

    

- 数据访问层(接口Dao+实现类DaoImpl)：专门用来进行数据库的增删改查操作，向上为业务逻辑层提供数据

  - UsersDao
  - UsersDaoImpl(UsersMapper.xml==>需要在业务逻辑层调用此文件中的功能)




不能跨层访问

界面层<==>业务逻辑层<==>数据逻辑层

web层<==>业务层<==>持久层

吃饭：

找服务员<==>厨师<==>采购员<==>菜市场

开发项目：

界面层<==>业务逻辑层<==>数据逻辑层<==>数据库

特点：

- 结构清晰、耦合度低，各层分工明确
- 可维护性高，可扩展性高
- 有利于标准化
- 开发人员可以只关注整个结构中的其中某一层的功能实现
- 有利于各层逻辑的复用

## 常用框架

- SSM:

  - Spring：它时整合其它框架的框架，它的核心时`IOC`(控制反转)和`AOP`(面向切面编程),它由20多个模块构成，在多领域都提供了很好的解决方案。
  - SpringMVC：它是Spring家族的一员，专门用来优化控制器(Servlet)的，提供了极简单数据提交，数据携带，页面跳转等功能。
  - MyBatis：是持久化层的一个框架，用来进行数据库访问的优化，专注于SQL语句，极大的简化了JDBC的范文

  框架定义：它是一个半成品软件，将所有的公共的，重复的功能解决掉，帮助程序猿快速高效的进行开发，它是可复用，可扩展的。

  

  做红烧鱼：摊主帮你处理好的鱼(半成品)

实现：

- 添加依赖
- 添加配置文件

1.新建maven项目，模板：quickstart模板

2.修改目录，添加缺失的目录，修改目录属性

3.修改pom.xml文件，添加mybatis、mysql依赖

4.修改pom.xml，添加资源文件指定

5.在idea中添加数据库的可视化

6.添加`jdbc.properties`属性文件(数据库的配置)

7.添加SqlMapConfig.xml(MyBatis的核心配置文件)

8.创建实体类`Student.java`，用于封装数据

9.添加完成学生表的增删改查的功能的`StudentMapper.xml`文件

9.创建测试类，进行功能测试

### pom.xml

`项目名/pom.xml`

```xml
<!--添加依赖-->
<dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>
   	  <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.4.6</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
<!--添加资源文件的指定，后可以将文件放到任意文件夹-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include>
                    <include>**/*.properties</include>
                </includes>

            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.xml</include>
                    <include>**/*.properties</include>
                </includes>

            </resource>
        </resources>
    </build>
```

### jdbc.properties

`main/resources/jdbc.properties`

```properties
jdbc.driverClassName=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/bbs?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
jdbc.username=root
jdbc.password=123456
```

### SqlMapConfig.xml

`main/resources/SqlMapConfig.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--头文件-->
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--mybatis-3-config.dtd/mybatis-config.xsd头文档描述文件-->
<configuration>
    <!--读取属性文件(jdbc.properties)
        属性：
            resource：从resources目录下找指定名称的文件加载
            url：使用绝对路径加载属性文件
    -->
    <properties resource="jdbc.properties"/>
    <!--设置日志底层执行的代码-->
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    <!--注册实体类的别名-->
    <typeAliases>
        <!--单个实体类别名注册-->
      <!--  <typeAlias type="team.planett.learnt.Model.UserData" alias="UserData"/>-->
        <!--批量注册别名
            别名为类名的驼峰命名法(规范)
            大驼峰：所有单词的首字母大写
            小驼峰：第一个的单词字母是小写，后面的单词首字母是大写
            类名、接口名规范都是大驼峰
            别名为小驼峰
        -->
        <package name="team.planett.learnt.Model"/>
    </typeAliases>
    <!--配置数据库的环境变量(数据库连接配置)-->
    <environments default="development">
        <!--default：属性为默认生效的环境，使用指定id进行环境切换-->
        <!--可以有多套，在不同环境使用-->
        <!--开发时在公司使用的数据库配置
            id：提供给environments的default属性使用
        -->
        <environment id="development">
            <!--配置事务管理器
                type:指定事务管理的方式(mybatis包里查找)，填写大写
                    JDBC:事务的控制交给程序员处理
                    MANAGED:由容器(Spring)来管理事务
            -->
            <transactionManager type="JDBC"></transactionManager>
            <!--配置数据源
                type:指定不同的配置方式
                    JNDI:java命名目录接口，在服务器端进行数据库连接池的股管理
                    POOLED:使用数据库连接池
                    UNPOOLED:不使用数据库连接池
                    客户端==>数据库连接池==>数据库
                    数据库连接池：做好与数据库连接（多个连接），客户端进行连接时，数据库连接池将连接给客户端(连接池的连接不断开与数据库的连接)，关闭时还回

            -->
            <dataSource type="POOLED">
                <!--配置数据库连接的基本参数(从jdbc.properties读取属性配置到POOLED数据库连接池的属性)
                    数据库连接池属性(jar包里找)
                    private String driver;
                    private String url;
                    private String username;
                    private String password;
                -->
                <property name="driver" value="${jdbc.driverClassName}"/>
                <property name="url" value="${jdbc.url}"/>
                <property name="username" value="${jdbc.username}"/>
                <property name="password" value="${jdbc.password}"/>
            </dataSource>
        </environment>
        <!--在家的数据库配置-->
        <environment id="home">
            <transactionManager type=""></transactionManager>
            <dataSource type=""></dataSource>
        </environment>
        <!--上线后的数据库配置-->
        <environment id="online">
            <transactionManager type=""></transactionManager>
            <dataSource type=""></dataSource>
        </environment>
    </environments>
    <!--注册mapper.xml文件
         resource：从resources目录下找指定名称的文件加载
         url：使用绝对路径加载属性文件
         class:动态代理方式下的注册
    -->
    <mappers>
        <mapper resource="UserMapper.xml"></mapper>
    </mappers>
</configuration>

```

### Student.java

`main/java/pojo/Student.java`

```java
// 参考IDEA自带Database连接数据库查看的数据类型
private Integer id;// 使用封装类型
private String name;
private String email;
private Integer age;
// 空构造方法和有参构造方法(不包含自增主键)
// setting和getting方法
// 重写toString()方法
```

### StudentMapper.xml

`main/resources/StudentMapper.xml`

// 为简化

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--
    mapper:是整个文件的大标签，用来开始和结束xml文件
        属性：
            namespace：指定命名空间(相当于包名)，用于区分不同mapper.xml文件中相同的id属性
-->
<mapper namespace="zq">
    <!--
        完成查询全部学生的功能
        List<Student> getALL();
            resultType(填写路径):指定查询返回结果集的类型，如果是集合，则必须是泛型的类型
            parameterType:如果有参数，则通过它来指定参数的类型(按参数查询)
    -->
    <select id="getAll" resultType="team.planett.learnt.Model.UserData">
        select * from t_user
    </select>
    <!--按主键id查询信息
        Student getById(Integer id);
    -->
    <select id="getById" parameterType="string" resultType="team.planett.learnt.Model.UserData">
        select id,name,password from t_user where id =#{id}
    </select>
    <!--按用户名模糊查询
    List<Student> getByName(String name);
    -->
    <select id="getByName" parameterType="string" resultType="team.planett.learnt.Model.UserData">
        select id,account,name,password,rid,create_time,accountStatus,onlineStatus from t_user where name like '%${_parameter}%'
    </select>
    <!--增加用户
    int insert(Student stu);
    增删改不需要返回值
    parameterType：传入参数为实体类
    #{id},#{account},#{name},#{password},#{create_time}:
        为实体类对应变量名的参数类型
    -->
    <insert id="insertUser" parameterType="team.planett.learnt.Model.UserData">
        insert into t_user (id,account,name,password,create_time) value (#{id},#{account},#{name},#{password},#{create_time})
    </insert>


    <!--按主键删除用户
        int delete(String id);
    -->
    <delete id="deleteUser" parameterType="string" >
        delete from t_user where id = #{id}
    </delete>

    <!--更新用户数据
        int update(Student stu)
    -->
    <update id="updateUser" parameterType="team.planett.learnt.Model.UserData">
        update t_user set name = #{name} ,password = #{password} where id = #{id}
    </update>
</mapper>
```

### MyTest.java

`test/java/.../MyTest.java`

// 未简化

```java
package team.planett.learnt;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import team.planett.learnt.Model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class MyTest {
    @Test
    public void testSql() throws IOException {
        // 使用文件流读取核心配置文件SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 取出SqlSession的对象
        SqlSession sqlSession = factory.openSession();
        // 完成查询操作
        List<UserData> list = sqlSession.selectList("zq.getAll");
        list.forEach(UserData -> System.out.println(UserData));
        // 关闭sqlSession
        sqlSession.close();
    }
    @Test
    public void testGetById() throws IOException {
        // 使用文件流读取核心配置文件SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 取出SqlSession的对象
        SqlSession sqlSession = factory.openSession();
        // 按值查询
        UserData userData = sqlSession.selectOne("zq.getById","00fa3102-e630-4dac-bb68-69ebac43ac77");
        System.out.println(userData);
        // 关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void testGetByName() throws IOException {
        // 使用文件流读取核心配置文件SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 取出SqlSession的对象
        SqlSession sqlSession = factory.openSession();
        // 模糊查询
        List<UserData> list = sqlSession.selectList("zq.getByName","用户");
        list.forEach(userData -> System.out.println(userData));
        // 关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void testInsertUser() throws IOException {
        // 使用文件流读取核心配置文件SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 取出SqlSession的对象
        SqlSession sqlSession = factory.openSession();
        // 插入数据
        String id = String.valueOf(UUID.randomUUID());
        String name = UUID.randomUUID().toString().substring(0,5);
        int num = sqlSession.insert("zq.insertUser",new UserData(id,name,"root3","123456"));
        System.out.println(num);
        // 注意：在所有的增删改后必须手工提交事务！！！
        sqlSession.commit();
        // 关闭SqlSession
        sqlSession.close();
    }

    @Test
    public void testDeleteUser() throws IOException {
        // 使用文件流读取核心配置文件SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 取出SqlSession的对象
        SqlSession sqlSession = factory.openSession();
        // 删除用户
        int num = sqlSession.insert("zq.deleteUser","06ec8223-fabc-489e-b129-ec48d52a2956");
        System.out.println(num);
        // 注意：在所有的增删改后必须手工提交事务！！！
        sqlSession.commit();
        // 关闭SqlSession
        sqlSession.close();
    }
    @Test
    public void testUpdateUser() throws IOException {
        // 使用文件流读取核心配置文件SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 取出SqlSession的对象
        SqlSession sqlSession = factory.openSession();
        // 删除用户
        int num = sqlSession.update("zq.updateUser",new UserData("af7da108-f0fe-472c-92e9-d4a82d4e0c45","root1","123456"));
        System.out.println(num);
        // 注意：在所有的增删改后必须手工提交事务！！！
        sqlSession.commit();
        // 关闭SqlSession
        sqlSession.close();
    }


}

```

// 简化后

```java
package team.planett.learnt;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import team.planett.learnt.Model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class MyTest {
    SqlSession sqlSession;

    @Before // 在所有@Test方法执行前先执行的代码
    public void openSqlSession() throws IOException {
        // 使用文件流读取核心配置文件SqlMapConfig.xml
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        // 创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
        // 取出SqlSession的对象
        sqlSession = factory.openSession();
    }
    @After
    public void closeSqlSession(){
        // 关闭SqlSession
        sqlSession.close();
    }


    @Test
    public void testSql() throws IOException {
        // 完成查询操作
        List<UserData> list = sqlSession.selectList("zq.getAll");
        list.forEach(UserData -> System.out.println(UserData));
    }
    @Test
    public void testGetById() throws IOException {
        // 按值查询
        UserData userData = sqlSession.selectOne("zq.getById","00fa3102-e630-4dac-bb68-69ebac43ac77");
        System.out.println(userData);
    }

    @Test
    public void testGetByName() throws IOException {
        // 模糊查询
        List<UserData> list = sqlSession.selectList("zq.getByName","用户");
        list.forEach(userData -> System.out.println(userData));
    }

    @Test
    public void testInsertUser() throws IOException {
        // 插入数据
        String id = String.valueOf(UUID.randomUUID());
        String name = UUID.randomUUID().toString().substring(0,5);
        int num = sqlSession.insert("zq.insertUser",new UserData(id,name,"root3","123456"));
        System.out.println(num);
        // 注意：在所有的增删改后必须手工提交事务！！！
        sqlSession.commit();
    }

    @Test
    public void testDeleteUser() throws IOException {
        // 删除用户
        int num = sqlSession.insert("zq.deleteUser","06ec8223-fabc-489e-b129-ec48d52a2956");
        System.out.println(num);
        // 注意：在所有的增删改后必须手工提交事务！！！
        sqlSession.commit();
    }
    @Test
    public void testUpdateUser() throws IOException {
        // 删除用户
        int num = sqlSession.update("zq.updateUser",new UserData("af7da108-f0fe-472c-92e9-d4a82d4e0c45","root1","123456"));
        System.out.println(num);
        // 注意：在所有的增删改后必须手工提交事务！！！
        sqlSession.commit();
    }

}
```

## 对象分析

- Resources类

  就是解析`SqlMapConfig`文件,创建出相应的对象

  ```java
  	  InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
  ```

- SqlSessionFactory接口

  使用Ctrl+h快捷键查看本接口的子接口及实现类(向上的向下的)

  `DefaultSqlSessionFactory`是实现类

  ```java
   SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
  ```

- SqlSession接口

  `DefaultSqlSession`是实现类

  

  为实体类注册别名

  `SqlMapConfig.xml`

  ```XML
  <typeAliases>
  	// 包围
   </typeAliases>
  ```

  - 单个注册

  ```xml
  <typeAlias type="team.planett.learnt.Model.UserData" alias="UserData"/>
  ```

  - 批量注册

  ```java
  <package name="team.planett.learnt.Model"/>
  ```

## 日志输出

```XML
  <!--设置日志底层执行的代码-->
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
```

```
Opening JDBC Connection
Created connection 1577592551.
Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@5e0826e7]
==>  Preparing: select * from t_user 
==> Parameters: (入参)
<==    Columns: id, account, name, rid, password, phone_number, create_time, accountStatus, onlineStatus, frdListID
<==        Row: 00fa3102-e630-4dac-bb68-69ebac43ac77, <<BLOB>>, 用户788e3, 1, 123456, 123456, 2022-04-09 18:32:32, 1, 0, 2072d
<==        Row: af7da108-f0fe-472c-92e9-d4a82d4e0c45, <<BLOB>>, null, 1, 123456, 123456, 2022-04-05 12:08:30, 1, 0, cfff7
<==      Total: 2
UserData{id='00fa3102-e630-4dac-bb68-69ebac43ac77', account='root2', name='用户788e3', password='123456', phone_number='123456', create_time=2022-04-10 02:32:32.0, accountStatus='1', onlineStatus='0', frdListID='2072d', frdDataListID='null', friendDataList=[]}
UserData{id='af7da108-f0fe-472c-92e9-d4a82d4e0c45', account='root', name='null', password='123456', phone_number='123456', create_time=2022-04-05 20:08:30.0, accountStatus='1', onlineStatus='0', frdListID='cfff7', frdDataListID='null', friendDataList=[]}
Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@5e0826e7]
Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@5e0826e7]
Returned connection 1577592551 to pool.

```









