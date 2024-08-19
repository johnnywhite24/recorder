package com.johniya.recorder.context;

import com.johniya.recorder.OperateUser;

public interface LogContext {

    String getContextId();

    OperateUser getOperateUser();

    String getOperateTarget();

    String getOperate();
}
