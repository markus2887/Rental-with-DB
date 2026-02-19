package com.ME.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "level", length = 1, nullable = false)
    private int level;

    @Column(name = "history", length = 3000)
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

    public List<Rental> getRentals() { return rentals; }


}