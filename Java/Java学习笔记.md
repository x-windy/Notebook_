# Java学习笔记

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

## JavaFX应用程序框架

```java
public class My_App extends Application {
    
    //main方法可不写,jvm自动调用launch()
	 public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primardyStage) throws Exception {
        primardyStage.setTitle("App");
        primardyStage.show();
        System.out.println("start");
    }
    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("init");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("stop");
    }
```

### Stage(舞台)

常用方法：`Title、icon、resiziable、StageStyle、Modality、event、show`

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
	
```

