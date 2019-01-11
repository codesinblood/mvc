package com.objectfrontier.training.java.service.util;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

public enum HttpMethod {

    GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD;

    public HttpUriRequest build(String uri) {

        switch (this) {
            case GET    :
                return new HttpGet(uri);
            case PUT    :
                return new HttpPut(uri);
            case POST   :
                return new HttpPost(uri);
            case DELETE :
                return new HttpDelete(uri);
            case PATCH  :
                return new HttpPatch(uri);
            case OPTIONS:
                return new HttpOptions(uri);
            case HEAD   :
                return new HttpHead(uri);
        }
        return null;
    }
}
