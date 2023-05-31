package it.intesys.academy.dto;


import java.util.Date;

// message data transfer object
public class MessageDTO {

    private String text;

    private Date timestamp;

    public MessageDTO(String text, Date timestamp) {
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
