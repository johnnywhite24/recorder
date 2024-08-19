package com.johniya.recorder;

import com.johniya.recorder.context.LogContext;

public interface OperateLogService {

    void logBefore(LogContext context, Object target);

    void logAfter(LogContext context, Object target);
}
