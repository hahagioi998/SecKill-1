实验中遇到的问题：

1、 问题：Junit4在pom.xml中已经配置完成，但用快捷键生成Test时没有找到Junit4
    
    解决方法：手动下载

2、 问题：连接不上数据库
   
    解决方法：1、延长连接等待时间  spring-dao.xml 中的checkoutTimeout 属性
             2、window下的username会出现异常 ，在jdbc.properties中把username 该为user即可

3、 问题：多个参数的查询方法无法执行

    原因：因为Java的语法不会报错形参的记录，所以例如queryAll(int offset,int limit)的方法，会被记录成queryAll(argv0,argv1)
          所以mybatis无法通过xml中的SQL语句中的offset属性找到对应的值
    
    解决方法：修改方法为queryAll(@Param("offset")int offset,@Param("limit")int limit)即可
