What's OpenWebFlow
===========

OpenWebFlow是基于Activiti扩展的工作流引擎，它针对Web环境下的工作流做了一些外围的包装工作。

以往的开发者往往会觉得Activiti不够用，或者发现它存在着某种不足：

* Activiti为Web应用提供了一个demo，但该demo绑架了很多的东西（如：表单的定义要按照它的样子来，自己的业务逻辑无处插入，等等）；
* Activiti的API很复杂（其实一点都不复杂，相反，它的设计很清晰，就是找起来有点麻烦），最好有一些快速使用的工具类；
* Activiti绑架了用户信息表的设计！这个是真正致命的，因为几乎每个业务系统都会属于自己的用户信息结构（包括User/Group/Membership），但不一定它存储在Activiti喜欢的那个库中，表的结构也不一定一样，有的时候，某些信息（如：动态的Group）压根儿就不采用表来存储；
* 流程模型允许指定每个活动的执行权限，Activiti对此支持得也很棒。但是，业务系统可能需要根据实际情况动态设置这些任务的执行权限（如：动态的Group）；

OpenWebFlow针对如上需求，做了一些工作。简单来说，OpenWebFlow框架：

* 提供了一系列的Tool工具类，你可以在java应用中、甚至在自己的Controller的方法里取到这些Tool；
* 包装了一系列的事件，注意这些事件是Web上下文的（不同于Activiti的事件机制），开发用户可以基于该事件机制写自己的EventHandler，比较方便的是，EventHandler的写法与Spring Controller的方法一样（支持参数自动映射）；
* 剥离了活动（activity）的权限管理，实现了与流程定义的解耦，即用户对活动的访问控制信息单独管理（而不是在流程定义中预先写死），这样有利于动态调整权限；
* 剥离了用户信息表的统一管理！客户程序可以忘掉Activiti的用户表、群组表、成员关系表；

开发者使用帮助：https://github.com/bluejoe2008/openwebflow/wiki

感谢咖啡兔<http://www.kafeitu.me/>，里面有很多的关于Activiti应用方案的讨论。
