package com.johniya.recorder.interceptor;

import com.johniya.recorder.OperateTargetSource;
import com.johniya.recorder.OperateUser;
import com.johniya.recorder.annotation.OperateId;
import com.johniya.recorder.annotation.OperateLog;
import com.johniya.recorder.context.DefaultLogContextFactory;
import com.johniya.recorder.context.LogContext;
import com.johniya.recorder.context.LogContextFactory;
import com.johniya.recorder.handlers.HandlerChain;
import com.johniya.recorder.handlers.LogHandlerRegister;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DefaultLogMethodInterceptor implements MethodInterceptor {

    private final ApplicationContext context;
    private final LogHandlerRegister register;
    private HandlerChain beforeHandlerChain;
    private HandlerChain afterHandlerChain;
    private LogContextFactory logContextFactory;
    private OperateUser operateUser;

    public DefaultLogMethodInterceptor(ApplicationContext context, LogHandlerRegister register, OperateUser operateUser) {
        this.context = context;
        this.register = register;
        beforeHandlerChain = register.buildBefore();
        afterHandlerChain = register.buildAfter();
        this.operateUser = operateUser;
    }

    public void setLogContextFactory(LogContextFactory logContextFactory) {
        this.logContextFactory = logContextFactory;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (logContextFactory == null) {
            logContextFactory = new DefaultLogContextFactory(operateUser);
        }
        Method method = methodInvocation.getMethod();
        Object[] objects = methodInvocation.getArguments();

        LogContext logContext = logContextFactory.createContext(method);

        OperateTargetSource source = this.getSource(method);
        Object id = this.getId(method, objects);

        Object before = source.getOperateTargetById(id);
        beforeHandlerChain.handleLog(before, logContext, beforeHandlerChain);

        Object res = methodInvocation.proceed();

        Object after = source.getOperateTargetById(id);
        afterHandlerChain.handleLog(after, logContext, afterHandlerChain);

        return res;
    }

    private OperateTargetSource getSource(Method method) {
        OperateLog operateLog = method.getDeclaredAnnotation(OperateLog.class);
        Class<? extends OperateTargetSource> sourceClass = operateLog.source();
        return context.getBean(sourceClass);
    }

    private Object getId(Method method, Object[] objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Integer idIndex = null;
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i ++) {
            if (parameters[i].isAnnotationPresent(OperateId.class)) {
                idIndex = i;
                break;
            }
        }
        if (idIndex != null) {
            return objects[idIndex];
        }
        for (Object o : objects) {
            Class tempClass = o.getClass();
            Field idField = null;
            Field[] fields = tempClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(OperateId.class)) {
                    idField = field;
                    break;
                }
            }
            if (idField != null) {
                Method idMethod = tempClass.getDeclaredMethod("get" + idField.getName().substring(0, 1).toUpperCase() + idField.getName().substring(1));
                return idMethod.invoke(o);
            }
        }
        throw new RuntimeException(String.format("Not found id field in [%s]", method.getName()));
    }
}
