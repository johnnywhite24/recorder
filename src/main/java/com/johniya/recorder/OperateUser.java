package com.johniya.recorder;

public interface OperateUser {

    default String defaultUser() {
        return "anonymous";
    }

    Object getCurrentOperateUser();
}
