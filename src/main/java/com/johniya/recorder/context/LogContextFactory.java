package com.johniya.recorder.context;

import java.lang.reflect.Method;

public interface LogContextFactory {

    LogContext createContext(Method method);
}
