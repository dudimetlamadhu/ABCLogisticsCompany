package com.alpha.ABCLogistics.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private int id;
    private String orderdate;
    private String status="placed";
    private int cost;
    
    @ManyToOne
    private Carrier carrier;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Cargo cargo;
    
    // FIX 2: Changed to @ManyToOne to allow multiple orders to share a Loading entity
    @ManyToOne 
    private Loading loading;
    
    // FIX 2: Changed to @ManyToOne to allow multiple orders to share an Unloading entity
    @ManyToOne
    private Unloading unloading;
    
    private String Mail;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOrderdate() {
        return orderdate;
    }
    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public Carrier getCarrier() {
        return carrier;
    }
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }
    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    public Loading getLoading() {
        return loading;
    }
    public void setLoading(Loading loading) {
        this.loading = loading;
    }
    public Unloading getUnloading() {
        return unloading;
    }
    public void setUnloading(Unloading unloading) {
        this.unloading = unloading;
    }
    public String getMail() {
        return Mail;
    }
    public void setMail(String mail) {
        Mail = mail;
    }
    public Order(int id, String orderdate, String status, int cost, Carrier carrier, Cargo cargo, Loading loading,
            Unloading unloading, String mail) {
        super();
        this.id = id;
        this.orderdate = orderdate;
        this.status = status;
        this.cost = cost;
        this.carrier = carrier;
        this.cargo = cargo;
        this.loading = loading;
        this.unloading = unloading;
        Mail = mail;
    }
    public Order() {
        super();
    }
}