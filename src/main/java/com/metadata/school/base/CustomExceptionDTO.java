package com.metadata.school.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class CustomExceptionDTO {
    private String error;
    private Date dateTime;

    public CustomExceptionDTO(String error, Date dateTime) {
        this.error = error;
        this.dateTime = dateTime;
    }

}
