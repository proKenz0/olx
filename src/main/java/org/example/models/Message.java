package org.example.models;

public class Message {
    private String id;
    private String thread_id;
    private String created_at;
    private String type;
    private String text;
    private boolean is_read;
    private Picture[] attachments;
    private String[] cvs;
    private String phone;

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", thread_id='" + thread_id + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                ", is_read=" + is_read +
                '}';
    }

    public Picture[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Picture[] attachments) {
        this.attachments = attachments;
    }

    public String[] getCvs() {
        return cvs;
    }

    public void setCvs(String[] cvs) {
        this.cvs = cvs;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public void setIs_read(boolean is_read) {
        this.is_read = is_read;
    }
}
