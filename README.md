## 操作日志记录工具

使用注解轻松完成统一的后台操作日志处理。（后台用户的操作行为记录，如：新增，编辑，删除等）

[使用案例][使用案例]

[使用案例]: https://github.com/johnnywhite24/recorder-example

使用注解在需要记录的地方

```java
@OperateLog(source = UserMockDao.class, target = Log.Target.USER, operate = Log.Operate.UPDATE)
@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public String updateUser(@RequestParam @OperateId String id, @RequestParam String name) {
    userService.updateUser(id, name);
    return "ok";
}
```


实现日志保存逻辑：

```java
@Service
public class TestOperateLogService implements OperateLogService {
    
    @Override
    public void logBefore(LogContext context, Object target) {
        // 保存日志
        context.getContextId();
        context.getOperateUser();
        ...
    }

    @Override
    public void logAfter(LogContext context, Object target) {

    }
}

```

