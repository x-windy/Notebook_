# Java学习笔记

## 

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

```
