package com.johniya.recorder.handlers;

import com.johniya.recorder.context.LogContext;

public class DefaultLogHandler implements LogHandler {

    @Override
    public void handleLog(Object target, LogContext context, HandlerChain handlerChain) {
        handlerChain.doNext(target, context);
    }
}
