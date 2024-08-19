package com.johniya.recorder.config;

import com.johniya.recorder.OperateLogService;
import com.johniya.recorder.context.LogContext;

import java.util.ArrayList;
import java.util.List;

public class OperateLogServiceWrapper implements OperateLogService {

    private List<OperateLogService> operateLogServiceList = new ArrayList<>();

    public OperateLogServiceWrapper(List<OperateLogService> operateLogServices) {
        if (operateLogServices != null) {
            operateLogServiceList = operateLogServices;
        }
    }

    @Override
    public void logBefore(LogContext context, Object target) {
        operateLogServiceList.forEach(s -> s.logBefore(context, target));
    }

    @Override
    public void logAfter(LogContext context, Object target) {
        operateLogServiceList.forEach(s -> s.logAfter(context, target));
    }
}
