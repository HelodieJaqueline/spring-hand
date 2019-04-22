### 为什么要用Spring?
* 对于业务开发来说，可以简化开发，降低模块间的耦合性。能让开发者尽可能把精力放在业务代码上。生态强大，各种开箱即用。

#### IOC
* Inversion Of Control  控制反转，开发者不需要自己管理各种对象的创建与维护，这项任务都交给了容器来做。最直观的感受就是不要自己去new很多对象，只需要声明就行了，SpringIOC会自动完成装配。
* IOC其实就是一个Map容器，存放了编程所需要的各种Bean,需要什么就从容器中拿什么。
* Spring通过扫描配置包路径下的带有标记(注解)的类，根据类名，通过反射生成bean的实例并将其放到一个Map中。


#### DI
* Dependency Injection 依赖注入 主要是维护各种Bean之间的依赖关系。而这种关系是在容器运行期才决定的。提升了组件重用的概率，使其更灵活、易扩展。
* Springt通过遍历IOC容器中的bean的Field，通过反射将其他bean注入到Field中

#### MVC
* 通过配置的DispatcherServlet找到Controller类下的Mapping,存放到一个url为key，method为value的Map中。当请求的相应url下的请求时，从这个Map中取出来，然后使用反射调用对应的方法即可。