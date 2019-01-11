package com.objectfrontier.training.java.service.servlet;

public class AuthorizationService {

    public static boolean checkAccess(boolean isAdmin) {

        if(isAdmin) {
            return true;
        }
        return false;
    }
}
