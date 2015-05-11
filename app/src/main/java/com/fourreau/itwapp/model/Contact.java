package com.fourreau.itwapp.model;

import java.sql.Timestamp;

/**
 * Created by Pierre on 06/05/2015.
 */
public class Contact {
    private String id;
    private String name;
    private String email;
    private Long deadline;

    public Contact(String id, String name, String email, Long deadline) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
