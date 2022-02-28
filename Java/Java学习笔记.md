# Java学习笔记

## JavaSE

### 基于接口的匿名内部类

`AnonymouslnnerClass.java`

> 需求：想使用一个接口，并创建对象
>
> 传统方法：写一个类，实现该接口，并创建对象

> 额外需求：该类只想使用一次，不想写一个类
>
> 引入基于接口的匿名内部类
>
> 方法(基本语法)：new 类或接口(){
>
> ​	类体
>
> };

```java
public class AnonymouslnnerClass {

    public static void main(String[] args) {
        /*
           编译看左边，运行看右边
           编译类型：AI
           运行类型：匿名内部类 XXXX => Test$1 分配类名

           // 匿名内部类只能使用一次，就消失了
           // 匿名内部类
           class XXXX implements AI{
            @Override
            public void say() {
            }
           }
           右边new了一个实现接口的类，在jdk底层创建了一个匿名内部类 Test$1
           并创建了Test$1对象实例，并返回地址
           底层操作：创建类—>实现接口—>new对象—>赋值给yly—>销毁类—>留下new对象
         */
        AI yly = new AI() {
            @Override
            public void say() {
                System.out.println("我是天才。");
            }
        };
        yly.say();
        System.out.println("yly的类名：" + yly.getClass());
    }

}
interface AI{
    public void say();
}
```

### 自定义异常类

`IllegalTriangleException.java`

```java
public class IllegalTriangleException extends Exception{
    IllegalTriangleException (double a,double b,double c){
        // 传递给异常对象的信息
        super("Invalid sides: "+a+"  "+b+"  "+c);
    }
}
class Triangle{
    static boolean isTriangle (double a,double b,double c){
        if(a+b>c && a+c>b && b+c>a){
            System.out.println("isTriangle");
            return true;
        }else {
            try {
                // 不是Triangle，抛出异常对象
                throw new IllegalTriangleException(a,b,c);
            } catch (IllegalTriangleException e) {
                // 捕获异常对象，并获取该异常对象的信息
                System.out.println(e.getMessage());
            }
            return false;
        }

    }

    public static void main(String[] args) {
        Triangle.isTriangle(1,2,4);
    }
}
```



## JDBC连接数据库
```java
    static final String DRIVER ="com.mysql.cj.jdbc.Driver";
	static final String URL="jdbc:mysql://location:3306/bbs?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8";
	/**
	 * URL语法格式如下：
	 * jdbc:mysql:是固定的写法，后面跟主机名localhost，3306是默认的MySQL端口号
	 * serverTimezone=UTC是指定时区时间为世界统一时间
	 * useUnicode=true是指是否使用Unicode字符集，赋值为true
	 * characterEncoding=utf-8是指定字符编码格式为UTF8
	 */
	static final String USER ="root";
	static final String PASSWORD ="123456";
	
	//mysql DML语句（增删改）
    private int jdbcDML(String sql) throws Exception {
        //查询数据库是否存在，并建立连接
		Class.forName(DRIVER);
		//建立数据库连接并返回Connection对象
		Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
		//返回statement对象用于执行静态的SQL语句
		Statement statement = connection.createStatement();
		return statement.executeUpdate(sql);
	}

    // Mysql DQL语句（查询）
    private ResultSet jdbcDQL(String sql) {

        ResultSet resultSet = null;
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }


```

## JavaFX

>  JavaFX开发说明文档：https://docs.oracle.com/javase/8/javafx/api/index.html

> JavaFX是用于构建富互联网应用程序的Java库。 使用此库编写的应用程序可以跨多个平台一致运行。使用JavaFX开发的应用程序可以在各种设备上运行，如台式计算机，手机，电视，平板电脑等。 更多请阅读：https://www.yiibai.com/javafx/javafx_overview.html 

### JavaFX的特性

以下是JavaFX的一些重要功能 -

- **使用Java语言编写**- JavaFX库都是用Java编写的，可用于在JVM上执行的语言，包括Java，Groovy和JRuby。这些JavaFX应用程序也是平台无关的。
- **FXML** - JavaFX采用称为FXML的语言，这是一种类似声明式标记语言的HTML。这种语言的唯一目的是定义用户界面。
- **Scene Builder** - JavaFX提供了一个名为Scene Builder(场景生成器)的应用程序。 在将此应用程序集成到IDE(如Eclipse和NetBeans)中时，用户可以访问拖放设计界面，用于开发FXML应用程序(就像Swing Drag＆Drop和DreamWeaver应用程序一样)。
- **Swing互操作性** - 在JavaFX应用程序中，可以使用Swing Node类嵌入Swing内容。 同样，可以使用JavaFX功能(如嵌入式Web内容和丰富的图形媒体)更新现有的Swing应用程序。
- **内置UI控件** - JavaFX库UI控件使用它可以开发一个全功能的应用程序。
- **类似CSS的样式** - JavaFX提供像样式的CSS。 通过使用它，可以使用CSS的简单知识改进应用程序的设计。
- **画布和打印API** - JavaFX提供了Canvas，即时模式样式的渲染API。 在包`javafx.scene.canvas`中，它包含一组用于canvas的类，可以使用它们直接在JavaFX场景的一个区域内绘制。JavaFX还在`javafx.print`包中提供用于打印目的的类。
- **丰富的API集合** - JavaFX库提供了一组丰富的API来开发GUI应用程序，2D和3D图形等。这套API还包括Java平台的功能。因此，使用此API，可以访问Java语言的功能，如通用，注释，多线程和Lambda表达式。传统的Java集合库已增强，包括可观察列表和映射等概念。使用这些，用户可以观察数据模型中的更改。
- **集成图形库** - JavaFX为2d和3d图形提供类。
- **图形管道** - JavaFX支持基于硬件加速图形管道(称为Prism)的图形。 当与支持的图形卡或GPU一起使用时，它提供平滑的图形。 如果系统不支持图形卡，则棱镜默认为软件渲染堆栈。

### JavaFX的生命周期

```java
public class My_App extends Application {
    
    //main方法可不写,jvm自动调用launch()
	 public static void main(String[] args) {
        Application.launch(args);
    }
    
      @Override
    public void init() throws Exception {
        super.init();
        System.out.println("init");
        //初始化准备
    }
    
    @Override
    public void start(Stage primardyStage) throws Exception {
        primardyStage.setTitle("App");
        primardyStage.show();
        System.out.println("start");
        //程序启动
    }
  

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("stop");
        //程序关闭
    }
```





### Stage(舞台)

常用方法：`Title、icon、resiziable、StageStyle、Modality、event、show`

常用属性：`x、y、hight、weigth`

```java
    primardyStage.setTitle("App");//设置程序标题

    primardyStage.getIcons().add(new Image("文件路径"));//设置程序图标

    primardyStage.setResizable(false);//设置窗口是否可变

    primardyStage.initStyle(StageStyle.UNDECORATED);//设置窗口样式
    //样式类型
    //DECORATED、UNDECORATED、TRANSPARENT、UTILITY、UNIFIED
    /*
    1) DECORATED——白色背景，带有最小化/最大化/关闭等有操作系统平台装饰（ 默认设置）
    2) UNDECORATED——白色背景，没有操作系统平台装饰
    3) TRANSPARENT——透明背景，没有操作系统平台装饰
    4) UTILITY——白色背景，只有关闭操作系统平台装饰
    5) UNIFIED——有操作系统平台装饰，消除装饰和内容之间的边框，内容背景和边框背景一致
    */
        
    primardyStage.initModality(Modality.NONE);//设置窗口模态
	/*
    APPLICATION_MODAL--设置应用模态，其它窗口被禁用
    WINDOW_MODAL--设置Windows模态,不会阻止其他窗体操作，需要设置父窗口
    stage0.initOwner(stage1)--stage0的拥有者是stage1
	*/
	

	// 取消系统默认的退出程序和关闭窗口动作
	Platform.setImplicitExit(false);//取消系统默认退出动作
 	primardyStage.setOnCloseRequest(event -> {
            event.consume();//取消关闭窗口动作
       
        // 添加退出窗口事件 (弹出提示框是否退出)
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("退出程序");
            alert.setHeaderText(null);
            alert.setContentText("您是否要退出程序？");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                primardyStage.close();//关闭窗口，后台仍在运行
                primardyStage.exit();//关闭程序
            }
        });

```

### Scene(场景)

```java
	primardyStage.setScene(scene);//设置舞台的场景

	scene.setCursor(new ImageCursor("文件路径"))//设置当前场景的光标图片
    
```

### Node(节点)

> 抽象类，所有控件继承该类
>
> 包括各种形状(2D或3D)、图像、多媒体、内嵌的Web浏览器、文本、UI控件、图表、组和容器

```java
label.setLayoutX(200);
label.setLayoutY(200);//设置控件位置

//设置背景颜色和边框颜色
label.setStyle("-fx-background:red;-fx-border-color:blue");//css语法

label.setPrefWidth(200);
label.setPrefHeigth(200);//设置自身的长宽高

label.setAlignment(Pos.CENTER);//设置内容居中

label.setVisible(false);//设置是否显示控件

label.setOpacity(0.5);//设置透明度

label.setRotate(90);//设置控件旋转90度

label.setTranslateX(60);
label.setTranslateX(100);//移动控件的位置

//3D旋转
scaleX/scaleY/scaleZ
```



###  Property(接口)

相关类：

> UI控件的属性绑定和属性监听

`bind、bindBidirectional、unbind、unbindBidirectional`

```java
circle.centerXProperty().bind(scene.widthProperty().divide(2))//单向绑定、圆适应场景X轴中间位置

circle.centerXProperty().addListener(new ChangerListener<Number>(){
    
})//属性改变监听器
```

### Layout(布局)

`BorderPane、GridPane、HBox、VBox...`

| 类名       | 布局中文名 |                                                         说明 |
| ---------- | :--------: | -----------------------------------------------------------: |
| AnchorPane |  锚点布局  |                           设置元素距离上、下、左、右多少像素 |
| BorderPane |  边框布局  | 设置元素在容器的上、下、左、右、中里面，中间区域的边距是受四边内元素影响的 |
| DialogPane | 对话框布局 |                                             可设置标题、内容 |
| FlowPane   |  流式布局  | 按顺序排列，可横向、竖向排列元素，到末尾重新换行或者换列排列，与Hbox、VBox不同的地方 |
| GridPane   |  网络布局  |                        按网格来布局，设置横、竖index来定位， |
| HBox       |  横向布局  |                          FlowPane 可以显示全，到末尾重新换行 |
| VBox       |  竖向布局  |                                 VBox、HBox超过了边界就被截了 |
| StackPane  |  堆叠布局  | 堆叠布局 ，元素会叠在一起，如果没给每一个元素单独设置位置，就叠在一起 |
| TextFlow   |   富文本   |                                 能把文字、图片、按钮搞在一起 |
| TilePane   |  砖块布局  |               跟那个啥布局很像，这个单个块大小，不影响其它块 |

#### 边框布局(BorderPane)

> BorderPane常用于定义一个非常经典的布局效果：上方是菜单栏和工具栏，下方是状态栏，左边是导航面板，右边是附加信息面板，中间是核心工作区域。

```java
```

![5-1-1-border](E:\GitHub\Notebook_md\Note_Resources\vx_images\5-1-1-border.png)

```java
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);//设置默认位置
    pane.setHgap(10);//设置行列之间的间隔
    pane.setVgap(10);
    pane.setPadding(new Insets(25,25,25,25));//设置内边距
	pane.setGridLinesVisible(true);//显示网格
	pane.add();

    Label sceneTitle = new Label("软件登录系统");
	pane.add(sceneTitle,0,1);//将标题放到第0列，第1行
```

#### 水平盒子(HBox)

`Padding/内边距、Margin/外边距、Spacing/节点间距、Style/样式`



### 事件驱动编程

相关类：`javafx.event.Event`





### 安装JavaFX Scene Builder

> **JavaFX Scene Builder**是一种可视布局工具，允许用户快速设计**JavaFX**应用程序用户界面，而无需编码。用户可以将**UI**组件拖放到工作区，修改其属性，应用样式表，并且它们正在创建的布局的`FXML`代码将在后台自动生成。它的结果是一个`FXML`文件，然后可以通过绑定到应用程序的逻辑与Java项目组合。 //更多请阅读：https://www.yiibai.com/javafx/install-javafx-scene-builder-into-eclipse.html 
>
> [JavaFX Scene Builder 1.x Archive (oracle.com)](https://www.oracle.com/java/technologies/javafxscenebuilder-1x-archive-downloads.html)
>
> 下载`javafx_scenebuilder-2_0-windows.msi`文件

#### 安装fx插件

![屏幕截图 2022-02-27 121225](E:\GitHub\Notebook_md\Note_Resources\vx_images\屏幕截图 2022-02-27 121225.png)

> 选中JavaFX插件

#### 指定JavaFX Scene Builder**可执行文件的路径**

![屏幕截图 2022-02-27 115659](E:\GitHub\Notebook_md\Note_Resources\vx_images\屏幕截图 2022-02-27 115659.png)

> 添加JavaFX Scene Builder的`启动程序.exe`路径

![屏幕截图 2022-02-27 115943](E:\GitHub\Notebook_md\Note_Resources\vx_images\屏幕截图 2022-02-27 115943.png)

> 右键`.fxml`文件可打开Scene Builder程序

![JavaFX Scene Builder](E:\GitHub\Notebook_md\Note_Resources\vx_images\JavaFX Scene Builder.png)
