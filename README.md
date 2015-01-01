What's OpenWebFlow
===========

OpenWebFlow是基于Activiti（官方网站<http://activiti.org/>，代码托管在<https://github.com/Activiti/Activiti>）扩展的工作流引擎，它针对Web环境下的工作流做了一些外围的包装工作。

Activiti的用户可能觉得它存在着一些待改进的地方：

* Activiti为Web应用提供了一个demo，但该demo绑架了很多的东西（如：表单的定义要按照它的样子来，自己的业务逻辑无处插入，等等）；
* Activiti绑架了用户信息表的设计！这个是真正致命的，因为几乎每个业务系统都会属于自己的用户信息结构（包括User/Group/Membership），但不一定它存储在Activiti喜欢的那个库中，表的结构也不一定一样，有的时候，某些信息（如：动态的Group）压根儿就不采用表来存储；
* Activiti允许在设计model的时候指定每个活动的执行权限，但是，业务系统可能需要根据实际情况动态设置这些任务的执行权限（如：动态的Group）；
* Activiti不支持中国特色^_^ 很多功能，如：催办、代办、加签（包括前加签/后加签）、自由跳转（包括前进/后退，omg!这还是工作流吗？）、分裂节点等；

OpenWebFlow针对如上需求，做了一些工作。简单来说，OpenWebFlow引擎：

* 提供了一系列的Tool工具类，你可以在java应用中、甚至在自己的Controller的方法里取到这些Tool，参见[工具类的使用](Tools) ；
* 包装了一系列的事件，注意这些事件是Web上下文的（不同于Activiti的事件机制），开发用户可以基于该事件机制写自己的EventHandler，比较方便的是，EventHandler的写法与Spring Controller的方法一样（支持参数自动映射），详见[OpenWebFlow的事件机制](Events)；
* 剥离了活动（activity）的权限管理，实现了与流程定义的解耦，即用户对活动的访问控制信息单独管理（而不是在流程定义中预先写死），这样有利于动态调整权限，详见[自定义活动权限管理](Activity-Permission-Management)；
* 剥离了用户信息表的统一管理！客户程序可以忘掉Activiti的用户表、群组表、成员关系表，详见[自定义用户成员关系管理](Custom-User-Group-Membership-Manager)；
* 允许运行时定义activity！__彻底满足“中国特色”，并提供了安全的（同时也是优雅的）催办、代办、加签（包括前加签/后加签）、自由跳转（包括前进/后）、分裂节点等功能__；

开发者使用帮助：https://github.com/bluejoe2008/openwebflow/wiki

感谢咖啡兔<http://www.kafeitu.me/>，里面有很多的关于Activiti应用方案的讨论。

#####OpenWebFlow也许是你见过的最好的Activiti增强引擎，也许不是，但它保证100%开源，原因很简单，它powered by Activiti。#####
#####你可以将OpenWebFlow应用于任何场合，而且你的产品不用提及OpenWebFlow的名字。但如果你能够在任何技术场合客观的评价并推广OpenWebFlow，我将感激不尽！同为开发者，我相信你也有这种情怀^_^#####
