package com.ME.entity;

import com.ME.service.RentalType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "startTime", length = 30, nullable = false)
    private LocalDateTime startTime;

    @Column(name = "endTime", length = 30)
    private LocalDateTime endTime;

    @Column(name = "price", length = 5, nullable = false)
    private double price;

    @Column(name = "totalPrice", length = 7)
    private double totalPrice;

    @Column(name = "level", length = 1, nullable = false)
    private int level;

    @Column(name = "daysToRent", length = 4)
    private double daysToRent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalType rentalType;

    protected Rental(){
    }

    public Rental(Member member, Long rentalObjectId, LocalDateTime startTime, LocalDateTime endTime, double price, double totalPrice, int level, double daysToRent, RentalType rentalType) {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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

    public void setDaysToRent(double daysToRent) {
        this.daysToRent = daysToRent;
    }

    public double getDaysToRent() {
        return daysToRent;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public void setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
    }
}
