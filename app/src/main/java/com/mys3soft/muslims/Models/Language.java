package com.mys3soft.muslims.Models;


import java.io.Serializable;

public class Language implements Serializable {
    private String language;
    private boolean selected;

    public Language(String language, boolean selected){
        this.language = language;
        this.selected = selected;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
