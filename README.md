# Event

1. 简介
> Event是一款用于在一个应用实例内发布和监听事件的高扩展能力事件总线，可与spring-boot无缝集成。

> 本组件已经上传到[Maven中央库](https://search.maven.org/search?q=g:org.antframework.event%20AND%20a:event)

2. 环境要求
> * JDK1.8及以上

3. 示例
> 结合示例看文档会更容易上手：https://github.com/zhongxunking/event-demo

4. 技术支持
> 欢迎加我微信（zhong_xun_）入群交流。<br/>
<img src="https://note.youdao.com/yws/api/personal/file/WEB6b849e698db2a635b43eba5bc949ce1c?method=download&shareKey=27623320b5ca82cbf768b61130c81de0" width=150 />

# 1. 引入事件总线依赖
```xml
<dependency>
    <groupId>org.antframework.event</groupId>
    <artifactId>event</artifactId>
    <version>1.0.0</version>
</dependency>
```

# 2. 配置
* 如果是spring-boot项目则不需要进行任何配置。
* 如果是非spring-boot项目则需要手动引入事件总线配置类EventBusConfiguration。比如：
```java
@Configuration
@Import(org.antframework.event.boot.EventBusConfiguration.class)
public class MyImport {
}
```

# 3. 使用事件总线
使用事件总线步骤：1、定义事件类型，2、定义事件监听器，3、发布事件
## 3.1 定义事件类型
事件类型就是普通的java类，不需要实现特定接口，也不需要打上特定注解。
```java
// 添加用户事件
public class AddUserEvent {
    // 用户id
    private String userId;
    // 用户名
    private String userName;

    public AddUserEvent(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    // 在此省略getter、setter
}
```

## 3.2 定义事件监听器
一个监听器内可以监听多种类型的事件，不同监听器可以监听同一种事件。
```java
// 用户监听器
@DomainListener(priority = 1)  // 事件监听器注解，属性priority表示优先级
public class UserListener {
    @org.antframework.event.annotation.Listen(priorityType = PriorityType.ASC) // 监听注解（priority值越小优先级越高）
    public void listenAddUser(AddUserEvent event) {
        // 监听到新增用户事件后，执行具体业务
    }

    @org.antframework.event.annotation.Listen(priorityType = PriorityType.DESC) // 监听注解（priority值越大优先级越高）
    public void listenDeleteUser(DeleteUserEvent event) {
        // 监听到删除用户事件后，执行具体业务
    }
}
```
一个事件发生后监听器执行顺序：先执行@Listen中priorityType=PriorityType.ASC的监听器，并按照优先级大小（@DomainListener的priority属性）由小到大执行；再执行@Listen中priorityType=PriorityType.DESC的监听器，并按照优先级大小由大到小执行。

## 3.3 发布事件
在需要发布事件的地方注入事件发布器EventPublisher
```java
@Autowired
private EventPublisher eventPublisher;
```
然后就可以发布事件：
```java
// AddUserEvent是自定义的事件类型
eventPublisher.publish(new AddUserEvent("123"，"zhangsan"));
```
