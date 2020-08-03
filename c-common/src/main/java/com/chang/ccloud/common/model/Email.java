package com.chang.ccloud.common.model;

/**
 * @Author changxizhao
 * @Date 2020/8/3 9:02
 * @Description
 */
public class Email {

    private String subject;

    private String receiver;

    private String text;

    private String textHtml;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public void setTextHtml(String textHtml) {
        this.textHtml = textHtml;
    }

    public Email(String subject, String receiver, String text, String textHtml) {
        this.subject = subject;
        this.receiver = receiver;
        this.text = text;
        this.textHtml = textHtml;
    }

    public Email() {
    }
}
