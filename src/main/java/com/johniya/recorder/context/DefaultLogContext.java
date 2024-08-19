package com.johniya.recorder.context;

import com.johniya.recorder.OperateUser;

public class DefaultLogContext implements LogContext {

    private String contextId;
    private OperateUser operateUser;
    private String operateTarget;
    private String operate;

    public DefaultLogContext(String contextId) {
        this.contextId = contextId;
    }

    @Override
    public String getContextId() {
        return this.contextId;
    }

    @Override
    public OperateUser getOperateUser() {
        return operateUser;
    }

    @Override
    public String getOperateTarget() {
        return operateTarget;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public void setOperateUser(OperateUser operateUser) {
        this.operateUser = operateUser;
    }

    public void setOperateTarget(String operateTarget) {
        this.operateTarget = operateTarget;
    }

    @Override
    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }
}
