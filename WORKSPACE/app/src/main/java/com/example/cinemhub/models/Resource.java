package com.example.cinemhub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Resource<T> /*implements Parcelable*/{
    private T data;
    private int totalResult;
    private int statusCode;
    private String statusMessage;

    public Resource(){}

    public Resource(T data, int totalResult, int statusCode, String statusMessage) {
        this.data = data;
        this.totalResult = totalResult;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
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

    /*@Override
    public String toString() {
        return "Resource{" +
                "data=" + data +
                ", totalResult=" + totalResult +
                ", statusCode=" + statusCode +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }

    protected Resource(Parcel in) {
        data = (T) in.readValue(T.class.getClassLoader());
        totalResult = in.readInt();
        statusCode = in.readInt();
        statusMessage = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(data);
        dest.writeInt(totalResult);
        dest.writeInt(statusCode);
        dest.writeString(statusMessage);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Resource> CREATOR = new Parcelable.Creator<Resource>() {
        @Override
        public Resource createFromParcel(Parcel in) {
            return new Resource(in);
        }

        @Override
        public Resource[] newArray(int size) {
            return new Resource[size];
        }
    };*/
}
