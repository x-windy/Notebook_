 # Git使用常见问题总结
 
 ## 已经commit了大文件
 
 > 解决方法:
 >
 
### 方法一（推荐）：
 
```shell

git rm -r --c "file"

# 找到仓库记录中的大文件
git rev-list --objects --all | grep "$(git verify-pack -v .git/objects/pack/*.idx | sort -k 3 -n | tail -5 | awk '{print$1}')"

# 重写这些大文件涉及到的所有提交
git filter-branch -f --prune-empty --index-filter 'git rm -rf --cached --ignore-unmatch {your-file-name}' --tag-name-filter cat -- --all


```



#### 方法二（不推荐）：
```shell
git reset --hard "hash值
```




 > [下载git-lfs插件](https://git-lfs.github.com/)
 
  追踪单个文件或文件夹
 
```shell
git lfs track "文件名"
git lfs track "*.扩展名"
``` 

```shell
 