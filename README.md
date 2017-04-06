实验中遇到的问题：
1、 问题：Junit4在pom.xml中已经配置完成，但用快捷键生成Test时没有找到Junit4
    解决方法：手动下载

2、 问题：连接不上数据库
    解决方法：1、延长连接等待时间  spring-dao.xml 中的checkoutTimeout 属性
             2、window下的username会出现异常 ，在jdbc.properties中把username 该为user即可
