What's OpenWebFlow
===========

OpenWebFlow是基于Activiti扩展的工作流引擎，确切的说，它的核心还是Activiti，OpenWebFlow更多的工作是针对Web环境做了一些包装工作。简单的说，OpenWebFlow：

* 提供了一系列的Tool工具类，你可以在java应用中、甚至在自己的Controller的方法里取到这些Tool；
* 包装了一系列的事件，注意这些事件是Web上下文的（不同于Activiti的事件机制），开发用户可以基于事件机制写自己的EventHandler，比较方便的是，EventHandler是基于注解的一些方法，这些方法的写法与Spring Controller的方法一样（支持参数自动映射）；
* 剥离了活动（activity）权限管理，即用户对活动的访问控制信息单独管理（而不是在流程定义中预先写死），这样有利于动态调整权限；

感谢咖啡兔<http://www.kafeitu.me/>，里面有很多的关于Activiti应用方案的讨论。
