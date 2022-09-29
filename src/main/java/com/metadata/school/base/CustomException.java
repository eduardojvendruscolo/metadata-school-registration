package com.metadata.school.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CustomException extends RuntimeException {
    private String error;
    private Date dateTime;

    public CustomException(String error) {
        super(error);
        this.error = error;
        this.dateTime = new Date();
    }

}
