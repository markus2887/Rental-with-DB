package com.ME.entity;

import com.ME.service.RentalType;
import jakarta.persistence.*;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @Column(name = "rentalObjectId", nullable = false)
    private Long rentalObjectId;

    @Column(name = "startTime", nullable = false)
    private String startTime;

    @Column(name = "endTime")
    private String endTime;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "daysToRent")
    private int daysToRent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalType rentalType;

    protected Rental(){
    }

    public Rental(Member member, Long rentalObjectId, String startTime, String endTime, double price, double totalPrice, int level, int daysToRent, RentalType rentalType) {
        this.member = member;
        this.rentalObjectId = rentalObjectId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.totalPrice = totalPrice;
        this.level = level;
        this.daysToRent = daysToRent;
        this.rentalType = rentalType;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) { this.member = member; }

    public Long getRentalObjectId() {
        return rentalObjectId;
    }

    public void setRentalObjectId(Long rentalObjectId) {
        this.rentalObjectId = rentalObjectId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDaysToRent(int daysToRent) {
        this.daysToRent = daysToRent;
    }

    public int getDaysToRent() {
        return daysToRent;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }
}
