package com.pedroperez.javaonmemoysearchcache.model;

public enum CatType {
    DOMESTIC("Domestic"),
    WILD("Wild");
    private final String description;

    CatType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
