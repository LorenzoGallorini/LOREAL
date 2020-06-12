package com.example.cinemhub.models;
/**
 * Classe generica che può essere per contenere i dati e gli stati della richiesta.
 *
 * @param <T> il tipo di dato associato con la richiesta.
 */
public class Resource<T> /*implements Parcelable*/{
    private T data;
    private int totalResult;
    private int statusCode;
    private String statusMessage;
    private boolean isLoading;

    /**
     * costruttore di default della classe Resource
     */
    public Resource(){}

    /**
     * costruttore della classe Resource
     * @param data  template della Resource
     * @param totalResult intero per indicare il numero totale dei risultati
     * @param statusCode intero che indica lo stato del codice
     * @param statusMessage stringa che contiene il messagio dello stato
     * @param isLoading booleano che indica che sta caricando
     */
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
