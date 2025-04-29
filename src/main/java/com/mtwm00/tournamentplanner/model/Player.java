package com.mtwm00.tournamentplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Player() {
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName +
                ", lastName='" + lastName +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
