package br.com.locadora.locadora.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @NotNull
    private User user;

    @OneToOne
    @NotNull
    private Movie movie;

    @Column
    private LocalDateTime dateLocation = LocalDateTime.now();

    @Column
    private LocalDateTime dateDevolution;

    public Location() {
    }

    public Location(long id, User user, Movie movie, LocalDateTime dateLocation, LocalDateTime dateDevolution) {
        this.id = id;
        this.user = user;
        this.movie = movie;
        this.dateLocation = dateLocation;
        this.dateDevolution = dateDevolution;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getDateLocation() {
        return dateLocation;
    }

    public void setDateLocation(LocalDateTime dateLocation) {
        this.dateLocation = dateLocation;
    }

    public LocalDateTime getDateDevolution() {
        return dateDevolution;
    }

    public void setDateDevolution(LocalDateTime dateDevolution) {
        this.dateDevolution = dateDevolution;
    }
}
