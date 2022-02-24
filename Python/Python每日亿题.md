# Python每日亿题
## 实现登录系统
>实现用户登录系统、连续登录三次错误后提示是否想继续登录(Y/N)、每次登录错误提剩余错误次数（提示:字符串格式化`%/format/f`）。
```python
count = 3
while count > 0:
    username = input("请输入用户名：")
    password = input("请输入登录密码：")
    if username == "zou" and password == "123456":
        print("登录成功！")
        break
    else:
        count-=1
        message1 = "用户名或者密码错误，剩余错误次数：{}".format(count)
        message2 = f"用户名或者密码错误，剩余错误次数：{count}"
        print(message1)
        print(message2)
        if count==0:
            choice = input("是否想继续登录(Y/N)?")
            if choice.upper() == "Y":
                count = 3
            elif choice == "N":
                continue
            else:
                print("输入错误！")

```
> 补充手机号动态验证码认证登录，并实现账号密码错误三次后生成动态验证码验证登录。
```python
```
## 实现验证码
>让用户输入验证码，如果左右有空格并且中间有空格，则去空格对比，并且除去大小写字母敏感。

```python
```
## 敏感词替换
>用户输入信息，"操、傻逼、草泥马..."等敏感词替换成相应的"* 、** 、***"
```python
```
## 排队买火车票