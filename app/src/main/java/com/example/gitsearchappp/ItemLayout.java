package com.example.gitsearchappp;

public class ItemLayout {
    private int mId;
    private String mRepositoryName;
    private String mDescription;
    private String mLanguage;

    public ItemLayout(int id, String repositoryName, String description, String language) {
        mId = id;
        mRepositoryName = repositoryName;
        mDescription = description;
        mLanguage = language;
    }

    public int getmId() {
        return mId;
    }

    public String getmRepositoryName() {
        return mRepositoryName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmLanguage() {
        return mLanguage;
    }
}
