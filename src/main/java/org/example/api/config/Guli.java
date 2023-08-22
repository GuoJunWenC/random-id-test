package org.example.api.config;

public abstract class Guli implements GuliInterface  {
    public void addUp(Integer inspectLog){
        System.out.println(getRoadSectionsResult());
    }
    protected abstract String getRoadSectionsResult();
}
