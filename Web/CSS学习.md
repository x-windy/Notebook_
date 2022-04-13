# CSS学习

- 层叠样式表

- 不是编程语言
- 告诉浏览器指定的样式、布局 

~~~css
p{
    color:red;
}

选择器,属性，值
Selector{
    Property:Value;
}

~~~

## 三种添加CSS方式

- 外部样式表、

  - CSS保存在`.css`文件中
  - 在HTML里使用<link>引用

- 内部样式表

  - 不使用外部CSS文件
  - 将CSS放在HTML<style>

- 内联样式

  - 仅影响一个元素
  - 在HTML元素的style属性中添加

  ~~~css
  <style>
      p{
  		color:red;
      }
  </style>
  <h1 style:"">Hello World</h1>
  ~~~

  