package com.alpha.ABCLogistics.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Loading {
    
    @Id
    // FIX 1: Use IDENTITY for guaranteed database-generated, non-zero ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String date;
    private String time;
    
    @OneToOne
    private Address address;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public Loading(int id, String date, String time, Address address) {
        super();
        this.id = id;
        this.date = date;
        this.time = time;
        this.address = address;
    }
    public Loading() {
        super();
    }
}