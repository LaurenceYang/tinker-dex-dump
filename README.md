#tinker-dex-dump

##关于tinker-dex-dump  
  
tinker-dex-dump是一个用来dump dex（指Tinker通过dexdiff算法生成的dex文件）的工具。
  
  
  
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
