package com.metadata.school.base;

import java.util.Date;

public class CustomExceptionDTO {
    private String error;
    private Date dateTime;

    public CustomExceptionDTO(String error, Date dateTime) {
        this.error = error;
        this.dateTime = dateTime;
    }

    public String getError() {
        return error;
    }

    public Date getDateTime() {
        return dateTime;
    }
}
