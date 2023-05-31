package it.intesys.academy.dto;

import java.util.Date;

public class MessageDto {
    private String message;
    private Date now;

    public MessageDto(String message, Date now) {
        if(message == null){
            throw new IllegalArgumentException("message cannot be null!!");
        }

        this.message = message;
        this.now = now;
    }

    public MessageDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if(message == ""){
            throw new IllegalArgumentException("message cannot be empty!!");
        }
        this.message = message;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }
}
