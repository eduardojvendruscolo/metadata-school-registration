package com.metadata.school.base;

import java.util.Date;

public class CustomException extends RuntimeException {

    private String error;
    private Date dateTime;

    public CustomException(String error, Exception e) {
        super(e);
        this.error = error;
        this.dateTime = new Date();
    }

    public CustomException(String error) {
        super(error);
        this.error = error;
        this.dateTime = new Date();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getDateTime() {
        return dateTime;
    }

}
