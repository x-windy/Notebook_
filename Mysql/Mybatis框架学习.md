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

## 三层架构
