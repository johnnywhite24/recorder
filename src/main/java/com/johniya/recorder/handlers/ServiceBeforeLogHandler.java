package com.johniya.recorder.handlers;

import com.johniya.recorder.config.OperateLogServiceWrapper;
import com.johniya.recorder.context.LogContext;

public class ServiceBeforeLogHandler implements BeforeLogHandler {

    private OperateLogServiceWrapper operateLogServiceWrapper;

    public ServiceBeforeLogHandler(OperateLogServiceWrapper operateLogServiceWrapper) {
        this.operateLogServiceWrapper = operateLogServiceWrapper;
    }

    @Override
    public void handleLog(Object target, LogContext context, HandlerChain handlerChain) {
        operateLogServiceWrapper.logBefore(context, target);
        handlerChain.doNext(target, context);
    }
}
