package com.fourreau.itwapp.model;

import org.apache.commons.codec.language.bm.Languages;

import java.sql.Timestamp;

/**
 * Created by Pierre on 06/05/2015.
 */
public class Contact {
    private String id;
    private String name;
    private String email;
    private Long deadline;
    private Language language;
    private Status status;
    private Boolean answered;

    public enum Language {
        FR("fr"),
        EN("en");

        private String language ;

        private Language(String language) {
            this.language = language ;
        }

        public String getLanguage() {
            return  this.language ;
        }
    }

    public enum Status {
        UNKNOWN(-1),
        EMAIL_SENT(0),
        OPEN_EMAIL(1),
        IN_PROGRESS(2),
        COMPLETED(3);

        private Integer status ;

        private Status(Integer status) {
            this.status = status ;
        }

        public Integer getStatus() {
            return  this.status ;
        }
    }


    public Contact(String id, String name, String email, Long deadline, String language, Integer status, Boolean answered) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.deadline = deadline;
        if(language.equals(Language.EN.getLanguage())) {
            this.language = Language.EN;
        }
        else {
            this.language = Language.FR;
        }

        switch (status) {
            case -1 :
                this.status = Status.UNKNOWN;
                break;
            case 0 :
                this.status = Status.EMAIL_SENT;
                break;
            case 1 :
                this.status = Status.OPEN_EMAIL;
                break;
            case 2 :
                this.status = Status.IN_PROGRESS;
                break;
            case 3 :
                this.status = Status.COMPLETED;
                break;
            default:
                this.status = Status.UNKNOWN;
                break;
        }
        this.answered = answered;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", deadline=" + deadline +
                ", language=" + language +
                ", status=" + status +
                ", answered=" + answered +
                '}';
    }
}
