package org.seckill.dto;

/**
 * 封装json结果，用泛型T
 * @param <T>
 */
public class SeckillResult<T> {

    private boolean seccess;

    private T date;

    private String error;


    public SeckillResult(boolean seccess, T date) {
        this.seccess = seccess;
        this.date = date;
    }

    public SeckillResult(boolean seccess, String error) {
        this.seccess = seccess;
        this.error = error;
    }

    public boolean isSeccess() {
        return seccess;
    }

    public void setSeccess(boolean seccess) {
        this.seccess = seccess;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
