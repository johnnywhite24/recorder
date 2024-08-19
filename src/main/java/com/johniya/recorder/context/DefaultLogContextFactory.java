package com.johniya.recorder.context;

import com.johniya.recorder.OperateUser;
import com.johniya.recorder.annotation.OperateLog;

import java.lang.reflect.Method;
import java.util.UUID;

public class DefaultLogContextFactory implements LogContextFactory {

    private OperateUser operateUser;

    public DefaultLogContextFactory(OperateUser operateUser) {
        this.operateUser = operateUser;
    }

    @Override
    public LogContext createContext(Method method) {
        DefaultLogContext context = new DefaultLogContext(UUID.randomUUID().toString());
        context.setOperateUser(operateUser);

        OperateLog operateLog = method.getAnnotation(OperateLog.class);
        String target = operateLog.target();
        context.setOperateTarget(target.equals("") ? null : target);
        String operate = operateLog.operate();
        context.setOperate(operate.equals("") ? null : operate);
        return context;
    }
}
