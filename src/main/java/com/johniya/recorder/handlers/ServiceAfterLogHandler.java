package com.johniya.recorder.handlers;

import com.johniya.recorder.config.OperateLogServiceWrapper;
import com.johniya.recorder.context.LogContext;

public class ServiceAfterLogHandler implements AfterLogHandler {

    private OperateLogServiceWrapper operateLogServiceWrapper;

    public ServiceAfterLogHandler(OperateLogServiceWrapper operateLogServiceWrapper) {
        this.operateLogServiceWrapper = operateLogServiceWrapper;
    }

    @Override
    public void handleLog(Object target, LogContext context, HandlerChain handlerChain) {
        operateLogServiceWrapper.logAfter(context, target);
        handlerChain.doNext(target, context);
    }
}
