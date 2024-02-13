package com.codecool.puzzleshowdown.repository.model;

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
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
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
    @OneToMany(mappedBy = "user")
    private List<Puzzle> solvedPuzzles;


    public User() {
    }

    public User(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.rating = 0;
        this.solvedPuzzles = new ArrayList<>();
        this.image = "data:image/webp;base64,UklGRsACAABXRUJQVlA4ILQCAAAQHwCdASrMAMwAPpFIoEolpKOhqVKoILASCWlu4XSRxnshYzO3MPRiTayksTWHGZ5Qtwq0NteTXRMU5nfIQLfgcdiTmDwkLqmxAQo2icCNUfIQF1csCUBqjZD/PEUc6hyUlbRMdh0jd4vXOY2xZvGIWwiy3HFP5I+PzufW18bJHGdeax/BkPorWE99Z5I+GZysuVELOKbqoB87bnkj4/GTmVEctFTnjTCvtTcTbkGEC31RIiA+vBvZyU4sSELR8PkDIJSOwGGpncDkT8tTRCy3fIyxoY69TDal6ni76lspQa8QXe7dwJcJw+sWGgOrhqfIzNPGknNVUOiuDIQLp7KqAAD+/vOywLPVHNOm0o2aci22oB9PA9jFoCjdym6EqSMQYxaEdsaSagbI9dNEO1M33HK5clYkOwTsx2n1ypzYw0svh3uQHx+5n5xpwrzOWMHEH28AcPVPlwyQJk63CGefi1wxKo0PQfxtXCP5QQKd0qpK2n5yV9w0iAJaBJwM6xDGsDCFOsksDXd2GMwbJWSi6RJQ3NJfDOy5v7HcT/D6Ce/55stFf+0JtQT6xyGW9vixBJ4G1kJ1nyZd2dRHpeV3TZE6lfXp3op58h80SM/0IyHKNWVueD/blUdoFdHZXAbAUGsRoBJCZrfHvVmnxbT4mfkrEe6eFnkqNrha0Dzxzl5CPQ5QLqrIbSMfMHlYqNbhQ6YWNtkutV1rocErGvUUZpgR2rQejHSXoQu6iDEYdVMn3bQTBkpkLOzCZIYgDGiavfTwQWp+caGtsgbMgDHLcunk4dw+3a3Yg155G9nIzpW4G076xgTmDqcAVxqi0pcJM/qyT3FYdfVKeGTxu5hmRfZ3f7NmvByy+ZIMGMwF+p2x0m/Us9896J44QpHJDmLA4WfQdl73TZnk4EIVfq9aYAAAAA==";
    }

    public User(String firstName, String lastName, String userName, String email, String password, int rating, String image, List<Puzzle> solvedPuzzles) {
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }

    public List<Puzzle> getSolvedPuzzles() {
        return solvedPuzzles;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setSolvedPuzzles(List<Puzzle> solvedPuzzles) {
        this.solvedPuzzles = solvedPuzzles;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
