# IDE集成开发环境使用指南

## IDEA

### 代码模板
> Editor--Live Templates (可定义，可建立选择适用语言的模板)
> Editor--General--Postfix Completion（只能用，不能改）
### 常用快捷键
 万能提示：alt +Enter

 main方法:
- `psvm`首字母
- `main`

 输出语句:`sout`

 搜索类：`ctrl + n`

 代码上下移动：`ctrl + shift + up/down`

 生成代码：`alt + insert `(构造方法、getter、setter、hashCode、equals、toString)

 整体重命名：`shift + F6`

 代码块包围：`ctrl + alt + t`


### IDEA的项目创建
> 1.建立的项目不是空项目，模块之间有数据的相会调用关系（分布式系统）
> 2.如果是空项目，那么模块之间没有任何关联，模块作为独立项目空间

- 创建空项目（Empty Project）-> 项目管理（Project Structure）
- 建立模块（Modules）

### 自动导包和多余包优化
> Editor--General--Auto Import-Java里两个勾上

```java
口 Add unambiguos...
口 Optimize imports...
```
### 忽略大小写提示
> Editor--General--Code Completion--口 Match case 取消勾选

### 显示多行类

> Editor--Genera-Editor Tabs--口 Show tavs in one row 取消勾选

### 类头注释消息
> Editor--File and Code Templates--Includes

### 设置编码模式
> Editor--File Encodeing

### 自动构建和自动编译
> Build Execution...--Compiler 勾选

```java
口 Build project auto...
口 Compile independent modules...
```
### 省电模式（Power Sava Mode）
> 部分功能关闭

### 序列化（Serializable）

> Editor--Inspections--serialVersionUID

### debug

### 配置构建工具

自动化构建工具:`maven,gradle`

一个默认的构建生命周期:
清理 -> 编译 -> 测试 -> 报告 -> 打包 -> 部署

> Build Execution...--Build Tools--Maven