# Maven

## 修改配置

`/conf/settings.xml`

添加阿里云镜像(提高jar包下载速度)：

```XML
<mirror>
<id>nexus-aliyun</id>
<mirrorOf>*</mirrorOf>
<name>Nexus aliyun</name>
<url>http://maven.aliyun.com/nexus/content/groups/public</url>
</mirror>
```

修改本地仓库地址：

```XML
<localRepository>D:\idea\ideamavendatabase</localRepository>
```

IDEA和Maven版本不兼容

```
org.codehaus.plexus.component.repository.exception.ComponentLookupException: com.google.inject.ProvisionException: Unable to provision, see the following errors:

1) Error injecting constructor, java.lang.NoSuchMethodError: org.apache.maven.model.validation.DefaultModelValidator: method <init>()V not found
  at org.jetbrains.idea.maven.server.embedder.CustomModelValidator.<init>(Unknown Source)
  while locating org.jetbrains.idea.maven.server.embedder.CustomModelValidator
  at ClassRealm[maven.ext, parent: ClassRealm[plexus.core, parent: null]] (via modules: org.eclipse.sisu.wire.WireModule -> org.eclipse.sisu.plexus.PlexusBindingModule)
  while locating org.apache.maven.model.validation.ModelValidator annotated with @com.google.inject.name.Named(value=ide)

1 error
      role: org.apache.maven.model.validation.ModelValidator
  roleHint: ide
```

