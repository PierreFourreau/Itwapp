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
    private String videoId;

    public InterviewDto(InterviewDto itw) {
        this.id = itw.getId();
        this.title = itw.getTitle();
        this.description = itw.getDescription();
        this.sent = itw.getSent();
        this.answers = itw.getAnswers();
        this.news = itw.getNews();
        this.videoId = itw.getVideoId();
    }


    public InterviewDto(String id, String title, String description, Integer sent, Integer answers, Integer news, String videoId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sent = sent;
        this.answers = answers;
        this.news = news;
        this.videoId = videoId;
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

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
