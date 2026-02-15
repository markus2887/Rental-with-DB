package com.ME.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "level", nullable = false, length = 1)
    private int level;

    @Column(name = "history", nullable = true)
    private String history;

    protected Member() {
    }

    public Member(String Name, int Level, String History) {
        this.name = Name;
        this.level = Level;
        this.history = History;
    }

    public Long getId() {
        return id;
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