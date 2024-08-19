package com.johniya.recorder.handlers;

import com.johniya.recorder.context.LogContext;

public interface LogHandler {

    void handleLog(Object target, LogContext context, HandlerChain handlerChain);
}
