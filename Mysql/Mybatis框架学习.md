# MyBatis框架学习

## 代理模式

名词：目标对象、代理对象、客户端

例：生产厂商、小卖部、学生

房东、中介、租房人

作用

- 控制目标对象的访问
- 增强功能

分类

- 静态代理
- 动态代理(JDK动态代理和CGLIB动态代理'子类代理')

### 静态代理

特点

- 目标对象和代理对象实现同一个业务接口
- 目标对象必须实现接口
- 目标对象在程序运行前就已经存在
- 能够灵活的进行目标对象的切换，却无法进行功能的灵活处理(使用动态代理解决此问题)

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

