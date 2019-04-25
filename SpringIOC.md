### Spring IOC
#### IOC&DI
* IOC(Inversion of Control)控制反转：所谓控制反转，就是把原先我们代码里面需要实现的对象创
  建、依赖的代码，反转给容器来帮忙实现。那么必然的我们需要创建一个容器，同时需要一种描述来让
  容器知道需要创建的对象与对象的关系。这个描述最具体表现就是我们所看到的配置文件。
* DI(Dependency Injection)依赖注入：就是指对象是被动接受依赖类而不是自己主动去找，换句话说就
  是指对象不是从容器中查找它依赖的类，而是在容器实例化对象的时候主动将它依赖的类注入给它。
  
#### 设计这样一个容器需要考虑哪些方面？
* 对象和对象的关系怎么表示？

  可以用 xml，properties 文件等语义化配置文件表示。
* 描述对象关系的文件存放在哪里？

  可能是 classpath，filesystem，或者是 URL 网络资源，servletContext 等。
* 不同的配置文件对对象的描述不一样，如标准的，自定义声明式的，如何统一？

  在内部需要有一个统一的关于对象的定义，所有外部的描述都必须转化成统一的描述定义。
* 如何对不同的配置文件进行解析？

需要对不同的配置文件语法，采用不同的解析器。
#### Spring 核心容器类
##### BeanFactory
Spring Bean 的创建是典型的工厂模式，这一系列的 Bean 工厂，也即 IOC 容器为开发者管理对象
间的依赖关系提供了很多便利和基础服务，在 Spring 中有许多的 IOC 容器的实现供用户选择和使用
其中 BeanFactory 作为最顶层的一个接口类，它定义了 IOC 容器的基本功能规范，BeanFactory 有三
个重要的子类：ListableBeanFactory、HierarchicalBeanFactory 和 AutowireCapableBeanFactory。
但是从类图中我们可以发现最终的默认实现类是 DefaultListableBeanFactory，它实现了所有的接口。那为何要定义这么多层次的接口呢？查阅这些接口的源码和说明发现，每个接口都有它使用的场合，它
##### BeanDefinition
SpringIOC 容器管理了我们定义的各种 Bean 对象及其相互的关系，Bean 对象在 Spring 实现中是
以 BeanDefinition 来描述的。
##### BeanDefinitionReader
Bean 的解析过程非常复杂，功能被分的很细，因为这里需要被扩展的地方很多，必须保证有足够的灵
活性，以应对可能的变化。Bean 的解析主要就是对 Spring 配置文件的解析。这个解析过程主要通过
BeanDefintionReader 来完成                                                
  