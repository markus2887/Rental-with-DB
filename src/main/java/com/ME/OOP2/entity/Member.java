package com.ME.OOP2.entity;

import java.util.Objects;

public class Member {
    private static int objectCount = 19;
    private int id;
    private String name;
    private int level;
    private String history;

    public Member() {
    }

    public Member(String Name, int Level, String History) {
        this.id = objectCount;
        this.name = Name;
        this.level = Level;
        this.history = History;
        objectCount++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }


}