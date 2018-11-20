package com.example.android.technews;

public class TechNews {
    private String title;
    private String webUrl;
    private String postedDate;
    private String sectionName;
    private String typeOfArticle;
    private String author;

    public TechNews(String title, String webUrl, String postedDate, String sectionName, String typeOfArticle, String author) {
        this.title = title;
        this.webUrl = webUrl;
        this.postedDate = postedDate;
        this.sectionName = sectionName;
        this.typeOfArticle = typeOfArticle;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getTypeOfArticle() {
        //capitalizing the first letter to look more polished. "Article published on dd/mm/yyyy"
        return typeOfArticle.substring(0, 1).toUpperCase() +
                typeOfArticle.substring(1, typeOfArticle.length());

    }

    public String getAuthor() {
        return author;
    }
}
