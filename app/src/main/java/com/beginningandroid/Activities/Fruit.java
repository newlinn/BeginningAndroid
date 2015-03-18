package com.beginningandroid.Activities;

/**
 * Created by LingChen on 15/2/25.
 */
public class Fruit {

    private String name;

    public String getName() {
        return this.name;
    }

    private int pic;

    public int getPic() {
        return this.pic;
    }

    public Fruit(String name, int pic) {
        this.name = name;
        this.pic = pic;
    }
}
