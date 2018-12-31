package com.benrkia.market.products;

public class Image {

    private long id;
    private String title;
    private String name;
    private String extension;

    public Image(long id, String name, String title, String extension) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.extension = extension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
