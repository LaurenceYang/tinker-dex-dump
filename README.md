#tinker-dex-dump

##序言  

[Tinker](https://github.com/Tencent/tinker)是微信推出的热更新开源项目,同其它热更新方案相比具有补丁包小，支持类,so，资源文件的替换等优点。其中在类替换的方案里自主研发了DexDiff算法，也正是DexDiff使补丁包变的更小。DexDiff算法最终生成的产物也是以.dex作为格式后缀，但和实际虚拟机中的dex文件是二种完全不同的结构。  

##关于tinker-dex-dump  
  
tinker-dex-dump是针对dexdiff生成的dex格式的文件，查看其内部数据的工具。主要目的是帮助大家对dexdiff生成的dex格式有一个更加直观的了解。如果使用该工具能让你更容易了解tinker的流程和tinker深层次的机制，那工具的目的也就达到了。  

  
  
##tinker-dex-dump的使用方法  
1、首先下载tinker-dex-dump.jar文件
[下载地址](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/lib/tinker-dex-dump.jar)
  
2、通过命令行方式执行  

**显示头部信息**
```
java -jar tinker-dex-dump.jar --dex classes.dex --header
```
![显示头部信息](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/asserts/command_show_header.png)

**显示section信息**
```
java -jar tinker-dex-dump.jar --dex classes.dex --section StringData
```
![显示section信息](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/asserts/command_show_section.png)

**显示帮助信息**
```
java -jar tinker-dex-dump.jar --help
```
![显示帮助信息](https://github.com/LaurenceYang/tinker-dex-dump/blob/master/asserts/command_show_help.png)
  
 

##参考  
[Tinker](https://github.com/Tencent/tinker)  

[热更新相关文档](https://github.com/LaurenceYang/article)  

[More...>>>](http://www.cnblogs.com/yyangblog/)
