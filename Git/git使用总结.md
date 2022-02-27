# Git使用常见问题总结


## 上传大文件


### 未commit大文件到本地库

  > [下载git-lfs插件](https://git-lfs.github.com/)

 安装 Git-lfs 命令行扩展（或直接编辑`.gitattributes`文件）

```shell
 git lfs install
```

 追踪单个文件或文件夹

```shell
git lfs track "文件名"

git lfs track "*.扩展名"
````

 先上传`.gitattributes`配置文件到`github`

 再进行大文件`commit`

### 已经commit了大文件，push到远程仓库报错

 解决方法:

### 方法一：
- 直接删除`.git`本地仓库文件

- 重新`clone`远程仓库，将工作区的文件复制到新`clone`的文件夹

- 再`add commit push`文件

### 方法二：
```shell
# 从.git的索引库中移除，对文件本身并不进行任何操作
git rm -r --cached "file"

# commit提交历史移动，记得备份工作区文件
git reset --hard "hash值

```

# 退出`log`内容过多和`vim`模式

## 1.`log`内容过多显示冒号
- 回车（往下滚一行）、空格（往下滚一页）可以继续查看剩余内容。

- 出现`（END）`表示到显示最后了

- 退出：英文状态下 按`q`可以退出`git log `状态。





## 2.退出vim模式


> [更多vim命令](https://zhuanlan.zhihu.com/p/61515833)
>
> `o，i，a`进入编辑模式
>
> 按`Esc`键退出编辑模式，进入命令模式

### 3.保存修改并退出：

英文模式下输入`:wq`或者 大写英文状态下输入`ZZ`，然后`回车`。

### 不保存退出：

英文模式下输入`:q!` 或者 `:qw!`，然后回车。

### 4.保存文件，不退出

输入`:w` ，回车后底行会提示写入操作结果，并保持停留在命令模式。

### 5.不保存修改，不退出：

输入`:e!` ，回车后回到命令模式。

# 未输入完成，显示`>`

快捷键`ctrl + d`,退出

# 修改已经commit的信息

[Git 修改已提交 commit 的信息](https://cloud.tencent.com/developer/article/1730774)
