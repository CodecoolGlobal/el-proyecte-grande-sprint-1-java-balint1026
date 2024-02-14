package com.codecool.puzzleshowdown.repository.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_name_unique", columnNames = "user_name"),
                @UniqueConstraint(name = "email_unique", columnNames = "email")
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;

    private String firstName;
    private String lastName;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private int rating;
    @Column(columnDefinition = "TEXT")
    private String image;
    @ManyToOne
    private Puzzle solvedPuzzles;


    public User() {
    }

    public User(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.rating = 0;
        this.solvedPuzzles = null;
        this.image = "default";
    }

    public User(String firstName, String lastName, String userName, String email, String password, int rating, String image, Puzzle solvedPuzzles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.rating = rating;
        this.solvedPuzzles = solvedPuzzles;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Puzzle getSolvedPuzzles() {
        return solvedPuzzles;
    }

    public void setSolvedPuzzles(Puzzle solvedPuzzles) {
        this.solvedPuzzles = solvedPuzzles;
    }
}
