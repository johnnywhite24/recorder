package com.johniya.recorder.handlers;

import com.johniya.recorder.context.LogContext;

public class HandlerChain implements LogHandler {

    private LogHandler logHandler;
    private HandlerChain next;

    public HandlerChain(LogHandler logHandler) {
        this.logHandler = logHandler;
    }

    @Override
    public void handleLog(Object target, LogContext context, HandlerChain next) {
        logHandler.handleLog(target, context, next);
    }

    public void doNext(Object target, LogContext context) {
        if (next != null) {
            next.handleLog(target, context, next);
        }
    }

    public HandlerChain getNext() {
        return next;
    }

    public void setNext(HandlerChain next) {
        this.next = next;
    }
}
