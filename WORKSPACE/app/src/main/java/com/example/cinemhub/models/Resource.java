package com.example.cinemhub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Resource<T> /*implements Parcelable*/{
    private T data;
    private int totalResult;
    private int statusCode;
    private String statusMessage;
    private boolean isLoading;


    public Resource(){}

    public Resource(T data, int totalResult, int statusCode, String statusMessage, boolean isLoading) {
        this.data = data;
        this.totalResult = totalResult;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.isLoading=isLoading;

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "data=" + data +
                ", totalResult=" + totalResult +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                ", isLoading=" + isLoading +
                '}';
    }
}
