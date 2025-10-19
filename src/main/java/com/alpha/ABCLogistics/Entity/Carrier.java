package com.alpha.ABCLogistics.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Carrier {

    @Id
    @NotNull
    @Positive
    private int id;

    private String name;

    @Email
    @Column(name = "email")  // map to DB column 'email'
    private String email;

    @NotNull
    @Positive
    @Column(name = "phone")  // map to DB column 'phone' (was 'contact' in entity)
    private long contact;

    public Carrier() {
        super();
    }

    public Carrier(@NotNull @Positive int id, String name, @Email String email, @NotNull @Positive long contact) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.contact = contact;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getContact() { return contact; }
    public void setContact(long contact) { this.contact = contact; }
}
