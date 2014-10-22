What's OpenWebFlow
===========

OpenWebFlow是基于Activiti扩展的工作流引擎，它针对Web环境下的工作流做了一些包装工作，以及一些其他的功能。简单的说，OpenWebFlow：

* 提供了一系列的Tool工具类，你可以在java应用中、甚至在自己的Controller的方法里取到这些Tool；
* 包装了一系列的事件，注意这些事件是Web上下文的（不同于Activiti的事件机制），开发用户可以基于该事件机制写自己的EventHandler，比较方便的是，EventHandler的写法与Spring Controller的方法一样（支持参数自动映射）；
* 剥离了活动（activity）的权限管理，实现了与流程定义的解耦，即用户对活动的访问控制信息单独管理（而不是在流程定义中预先写死），这样有利于动态调整权限；
* 剥离了用户群管理！客户程序可以忘掉Activiti的用户表、群组表、成员关系表；

感谢咖啡兔<http://www.kafeitu.me/>，里面有很多的关于Activiti应用方案的讨论。


如何开始？|https://github.com/bluejoe2008/openwebflow/wiki/How-to-start
ProcessEngineTool工具类的使用
客户代码中的Controller怎么写？
事件机制

