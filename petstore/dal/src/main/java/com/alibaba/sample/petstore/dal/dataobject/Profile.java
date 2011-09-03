package com.alibaba.sample.petstore.dal.dataobject;

public class Profile {
    private final User user;
    private String favoriteCategoryId;
    private String languagePreference;
    private boolean listOption;

    public Profile(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getFavoriteCategoryId() {
        return favoriteCategoryId;
    }

    public void setFavoriteCategoryId(String favoriteCategoryId) {
        this.favoriteCategoryId = favoriteCategoryId;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public boolean isListOption() {
        return listOption;
    }

    public void setListOption(boolean listOption) {
        this.listOption = listOption;
    }
}
