package com.fourreau.itwapp.model;

/**
 * Created by Pierre on 06/05/2015.
 */
public class InterviewDto {
    private String id;
    private String title;
    private String description;
    private Integer sent;
    private Integer answers;
    private Integer news;

    public InterviewDto(String id, String title, String description, Integer sent, Integer answers, Integer news) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sent = sent;
        this.answers = answers;
        this.news = news;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
    }

    public Integer getAnswers() {
        return answers;
    }

    public void setAnswers(Integer answers) {
        this.answers = answers;
    }

    public Integer getNews() {
        return news;
    }

    public void setNews(Integer news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "InterviewDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sent=" + sent +
                ", answers=" + answers +
                ", news=" + news +
                '}';
    }
}
