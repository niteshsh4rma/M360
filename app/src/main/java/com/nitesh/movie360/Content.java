package com.nitesh.movie360;

import java.io.Serializable;
import java.util.Map;

public class Content implements Serializable {


    private String title;
    private String description;
    private String pic;
    private String video;
    private String year;
    private String quality;
    private String category;
    private Map<String, String> episodeList;

    public Content(){}

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public Map<String, String> getEpisodeList() { return episodeList; }

    public void setEpisodeList(Map<String, String> episodeList) { this.episodeList = episodeList; }

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getYear() { return year; }

    public void setYear(String year) { this.year = year; }

    public String getQuality() { return quality; }

    public void setQuality(String quality) { this.quality = quality; }






}
