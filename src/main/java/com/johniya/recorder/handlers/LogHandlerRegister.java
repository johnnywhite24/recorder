package com.johniya.recorder.handlers;

import java.util.HashMap;
import java.util.Map;

public class LogHandlerRegister {

    private final Map<Integer, LogHandler> beforeHandlerMap = new HashMap<>();
    private final Map<Integer, LogHandler> afterHandlerMap = new HashMap<>();

    public void registerBefore(int order, LogHandler logHandler) {
        beforeHandlerMap.put(order, logHandler);
    }
    public void registerAfter(int order, LogHandler logHandler) {
        afterHandlerMap.put(order, logHandler);
    }

    public HandlerChain buildBefore() {
        HandlerChain handlerChain = new HandlerChain(new DefaultLogHandler());
        HandlerChain current = handlerChain;

        Integer[] keyList = beforeHandlerMap.keySet().stream().sorted(Integer::compareTo).toArray(Integer[]::new);
        for (int i = 0; i < keyList.length; i ++) {
            LogHandler logHandler = beforeHandlerMap.get(keyList[i]);
            HandlerChain temp = new HandlerChain(logHandler);
            current.setNext(temp);
            current = temp;
        }
        return handlerChain;
    }

    public HandlerChain buildAfter() {
        HandlerChain handlerChain = new HandlerChain(new DefaultLogHandler());
        HandlerChain current = handlerChain;

        Integer[] keyList = afterHandlerMap.keySet().stream().sorted(Integer::compareTo).toArray(Integer[]::new);
        for (int i = 0; i < keyList.length; i ++) {
            LogHandler logHandler = afterHandlerMap.get(keyList[i]);
            HandlerChain temp = new HandlerChain(logHandler);
            current.setNext(temp);
            current = temp;
        }
        return handlerChain;
    }
}
