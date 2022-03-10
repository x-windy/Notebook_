# Effective Java阅读笔记

## 第2章：创建和销毁对象

### 第1条：用静态方法代替构造方法

#### 提供一个公有的构造方法

- 构造方法没有描述正被返回的对象的名称

- 多个构造方法时，记不住用哪个，容易调用错误的构造方法

```java
public NutritionFacts(int servingSize, int servings) {
        this.servingSize = servingSize;
    	this.servings = servings;
    }
```



#### 提供一个公有的静态方法

##### 优点

- 只是一个返回类的实例的静态方法
- 有名称，可通过名称区分
- 每次调用它们时，不必每次都创建一个新对象(避免构造重复的对象)
- 可以返回原返回类型的任何子类的对象(?)
- 所有返回的对象的类可以随着每次调用而发生变化
- 方法返回的对象所属的类，在编写包含该静态方法时的类时可以不存在(?)

##### 缺点

- 类如果不含公有的或者受保护大的构造方法，就不能被子类化
- 很难发现它，在API文档中，没有明确标识出

##### 静态方法常用方法名称

`from、of、valueOf、getInstance、newInstance、getType、newType`

- `from`——类型转换方法，只有一个参数，返回该类型的一个相应的实例：`Date d = Date.from(instant);`
- `of`——聚合方法，带有多个参数，返回该类型合并起来的一个实例：`Set<Rank> faceCads = EnumSet.of(JACK,TOM,KING);`
- `valueOf`比`from`和`of`——更繁琐的一种代替方法：`BigInteger prime =  BigInteger.valueOf(Integer.MAX_VALUE); `
- `instance`或者 `getInstance` ——返回的实例是通过方法的（如有）参数来描述的，但是不能说与参数具有同样的值：`StackWalker  luke= StackWalker.getInstance(options);`
- `create`或者``newInstance` ——像`instance`或者 `getInstance` 一样，但`create`或者 `newInstance `能够确保每次调用都返回一个**新的实例**：`Objectnew Array = Array.newInstance(class0bject,arrayLen);`
- `getType`——像 `getInstance `一样，但是在静态方法处于**不同的类**中的时候使用。Type表示静态方法所返回的对象类型：`FileStorefs = Files.getFileStore(path);`
- `newType`——像 `newInstance` 一样，但是在静态方法处于**不同的类**中的时候使用。`Type`表示静态方法所返回的对象类型：`BufferedReader br = Files.newBufferedReader(path);`
- `type`——`getType`和`newType`的简版：`List< Complainty> litany= Collections.list(legacyLitany);`

JDBC连接数据库

```java
public class Jdbc_User {
    static final String DRIVER ="com.mysql.cj.jdbc.Driver";
    static final String URL="jdbc:mysql://localhost:3306/bbs?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
    static final String USER ="root";
    static final String PASSWORD ="123456";

    public static void main(String[] args) {
        Connection conn = Jdbc_User.getConn();
        System.out.println(conn);
    }

    public static Connection getConn() {
        Connection connection = null;
        try {
            //查询数据库是否存在，并建立连接
            Class.forName(DRIVER);
            //建立数据库连接并返回Connection对象
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



    // Mysql DML语句（增删改）
    public static int jdbcDML(String sql) {
        Statement statement = null;
        Connection connection = Jdbc_User.getConn();
        int rows = 0;
        try {
            statement = connection.createStatement();
            rows = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    // Mysql DQL语句（查询）
    public static ResultSet jdbcDQL(String sql) {
        ResultSet resultSet = null;
         Connection connection = Jdbc_User.getConn();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
   
```



### 第2条：多个构造器参数时解决方法

要求：用一个类表示包装食品外面显示的营养成分表，成分表中有几个标签的域(值)是必须的：含量、卡路里。超过20个可选的域：总脂肪量、饱和脂肪量、胆固醇、钠等等。某几个可选域都会有非零值。

#### 重叠构造器模式（传统）

- 难阅读、难编写
- 参数顺序搞错会出现错误行为

```java
public class NutritionFacts {
    private final int servingSize ;//(mL)
    private final int servings;//(per container)
    private final int calories;//(per serving)
    private final int fat;//(ɡ/serving)
    private final int sodium;//(mg/serving)
    private final int carbohydrate ;//(g/serving)

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize,servings,0);
        // 调用下一条构造方法
    }
    public NutritionFacts (int servingSize, int servings, int calories){
        this(servingSize,servings,calories,0);
        // 调用下一条构造方法
    }
    public NutritionFacts (int servingSize, int servings, int calories,int fat){
        this(servingSize,servings,calories,fat,0);
         // 调用下一条构造方法
    }
    public NutritionFacts (int servingSize, int servings, int calories,int fat,int sodium){
        this(servingSize,servings,calories,fat,sodium,0);
         // 调用下一条构造方法
    }
    public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingSize = servingSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
         // 赋值
    }

    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts(240,8,100,0,35,27);
    }

}
```

#### JavaBeans模式

- 构造实例容易
- 代码易读
- 不能设置不可变成员
- 无法通过检验构造器参数的有效性来保证一致性(?)

```java
public class NutritionFacts {
    private int servingSize = -1;//(mL) 无默认值
    private int servings = -1;//(per container) 无默认值
    private int calories = 0;//(per serving)
    private int fat = 0;//(ɡ/serving)
    private int sodium = 0;//(mg/serving)
    private int carbohydrate = 0;//(g/serving)
    // 构造方法
    public NutritionFacts(){}
	// setter方法
    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts();
        // 调用私有的setter方法
        cocaCola.setServingSize(240);
        cocaCola.setServings(8);
        cocaCola.setCalories(100);
        cocaCola.setFat(0);
        cocaCola.setSodium(35);
        cocaCola.setCarbohydrate(27);
    }
}
```

#### Builder建造者模式(最优)

- 能保证重叠构造器模式那样的安全性
- 也能保证像JavaBeans模式的可读性
- Builder是它构建的类的静态成员类
- Builder模式模拟了具名的可选参数

> 1.外部类成员为不可变成员，并构建一个参数变量为`Builder(int v...)`的构造方法(该变量用于调用赋值方法，给外部类成员赋值)
>
> 2.构建一个静态成员类，并定义不不可变成员和可变成员(可选域，不想设置的参数)
>
> 3.构建以``Builder`为变量，参数为可变成员的多个赋值方法，并返回Builder对象本身（方便调用其它赋值方法）
>
> 4.最后构建以外部类为变量的``build()``，**返回外部类对象**，并调用外部类对象的构造方法，传递Builder对象本身
>
> 5.最后构造外部类实例：`外部类 变量名 = new 外部类.静态成员类(val1,val2) .赋值方法1(val3).赋值方法2(val4)...build()`

```java
public class NutritionFacts {
    private final int servingSize ;//(mL)
    private final int servings;//(per container)
    private final int calories;//(per serving)
    private final int fat;//(ɡ/serving)
    private final int sodium;//(mg/serving)
    private final int carbohydrate ;//(g/serving)

    // 静态成员类
    public static class Builder{
        private final int servingSize ;//(mL)
        private final int servings;//(per container)

        private int calories = 0;//(per serving)
        private int fat = 0;//(ɡ/serving)
        private int sodium = 0;//(mg/serving)
        private int carbohydrate = 0;//(g/serving)

        // 构造方法
        public Builder(int servingSize, int servings){
            this.servingSize = servingSize;
            this.servings = servings;
        }

        // 返回this对象(本身)
        public Builder calories(int val){
            calories = val;
            return this;
        }
        public Builder fat(int val){
            fat = val;
            return this;
        }
        public Builder sodium(int val){
            sodium = val;
            return this;
        }
        public Builder carbohydrate(int val){
            carbohydrate = val;
            return this;
        }
        // 将builder对象传递给NutritionFacts(Builder builder)构造方法
        public NutritionFacts build(){
            return new NutritionFacts(this);
        }
    }
    // 构造方法
    public NutritionFacts(Builder builder){
        // 调用builder对象的成员赋值
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static void main(String[] args) {
        // new 一个NutritionFacts.Builder()对象.sodium(35)并返回一个Builder(本身)对象...build()调用NutritionFacts的构造方法并返回一个对象
        // new NutritionFacts.Builder(240,8).calories(100).sodium(35).carbohydrate(27).build();
        NutritionFacts cocaCola = new NutritionFacts.Builder(240,8).calories(100).sodium(35).carbohydrate(27).build();

    }
}

```

