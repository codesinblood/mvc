package com.objectfrontier.training.java.service.service;

import com.objectfrontier.training.java.service.service.ErrorCode;

public class AppException extends RuntimeException {

    AppException(ErrorCode errorMsg){
        super(errorMsg.getErrorMessage());
    }
}
