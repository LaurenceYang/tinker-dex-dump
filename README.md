# tinker-dex-dump

## 序言  

[Tinker](https://github.com/Tencent/tinker)是微信推出的热更新开源项目，同其它热更新方案相比具有补丁包小，支持类,so，资源文件的替换等优点。其中在类替换的方案里自主研发了DexDiff算法，使得补丁包变的更小。DexDiff算法最终生成的产物虽然也以.dex作为格式后缀，但和实际虚拟机中的dex文件是二种完全不同的格式。  

## 关于tinker-dex-dump  
  
tinker-dex-dump是针对dexdiff生成的.dex格式文件，查看其内部数据的工具。主要目的是帮助大家对dexdiff生成的dex格式有一个更加直观的了解。如果使用该工具能让你更容易了解tinker相关原理，那该工具的目的也就达到了，也算是个人对Tinker的致敬。  

  
  
## tinker-dex-dump的使用方法  
1、首先下载tinker-dex-dump.jar文件  

[下载地址](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/lib/tinker-dex-dump.jar)
  
2、通过命令行方式执行  
### 命令格式
  
 ```
 java -jar tinker-dex-dump.jar --dex *.dex [--header] [--section section-name]
 ```
 --dex            必选项，后接需要dump的dex路径  
 
 --header      可选项，显示header区域信息
 
 --section      可选项，显示section区域信息，后接要显示的section名字  
 
 
#### --section参数列表，参数的意义同其命名
 
> * StringData
> * TypeId
> * ProtoId
> * FieldId
> * MethodId
> * ClassDef
> * TypeList
> * AnnotationSetRefList
> * AnnotationSet
> * ClassData
> * Code
> * DebugInfo
> * Annotation
> * StaticValue
> * AnnotationsDirectory
 
### 范例一：显示头部信息  
```
java -jar tinker-dex-dump.jar --dex classes.dex --header
```
![显示头部信息](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/asserts/command_show_header.png)

### 范例二：显示StringData区域信息   
```
java -jar tinker-dex-dump.jar --dex classes.dex --section StringData
```
![显示section信息](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/asserts/command_show_section.png)

### 范例三：显示帮助信息  
```
java -jar tinker-dex-dump.jar --help
```
![显示帮助信息](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/asserts/command_show_help.png)
  
## tinker dex格式参考  
tinker dex格式主要包括两大部分：头部和各个section区域的操作列表，如下图：
![tinker dex format](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/asserts/dex%20format.png)  
  
更多关于tinker dex格式可以参考Tinker项目的[DexPatchGenerator](https://github.com/Tencent/tinker/blob/master/tinker-build/tinker-patch-lib/src/main/java/com/tencent/tinker/build/dexpatcher/DexPatchGenerator.java)文件  

## TODO  
  
1、复杂区域如ClassData等的表现形式  

也欢迎大家多多提出意见  



## 参考  
[Tinker官方Github](https://github.com/Tencent/tinker)  

[Tinker官方Wiki](https://github.com/Tencent/tinker/wiki)  

[热更新相关文档](https://github.com/LaurenceYang/article)  

[More...>>>](http://www.cnblogs.com/yyangblog/)
