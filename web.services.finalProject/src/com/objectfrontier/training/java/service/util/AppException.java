package com.objectfrontier.training.java.service.util;

import java.util.ArrayList;
import java.util.List;

public class AppException extends RuntimeException {

    List<ErrorCode> errorList;

    public AppException(ErrorCode errorMsg){
        super();
        this.errorList = new ArrayList<>();
        errorList.add(errorMsg);
    }

    public AppException(ErrorCode e, Throwable Cause) {
        super(Cause);
        this.errorList = new ArrayList<>();
        errorList.add(e);
    }

    public AppException(List<ErrorCode> errorCodes) {
//        super();
        this.errorList = errorCodes;
    }

    public List<ErrorCode> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<ErrorCode> errorList) {
        this.errorList = errorList;
    }
}
