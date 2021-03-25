package org.example.models;

public class OlxThread {
    private String id;
    private String advert_id;
    private String interlocutor_id;
    private int total_count;
    private int unread_count;
    private String created_at;
    private boolean is_favourite;

    @Override
    public String toString() {
        return "Thread{" +
                "id='" + id + '\'' +
                ", total_count=" + total_count +
                ", unread_count=" + unread_count +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public String getInterlocutor_id() {
        return interlocutor_id;
    }

    public void setInterlocutor_id(String interlocutor_id) {
        this.interlocutor_id = interlocutor_id;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getUnread_count() {
        return unread_count;
    }

    public void setUnread_count(int unread_count) {
        this.unread_count = unread_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(boolean is_favourite) {
        this.is_favourite = is_favourite;
    }
}
