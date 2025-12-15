package de.adrianwalter.movie_list_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class MovieList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany( mappedBy = "movieList", cascade = CascadeType.ALL)
    private List<Movie> movies;

    @NotBlank
    private String name;

    private String description;

    private String readKey;
    private String writeKey;

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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReadKey() {
        return readKey;
    }

    public void setReadKey(String readKey) {
        this.readKey = readKey;
    }

    public String getWriteKey() {
        return writeKey;
    }

    public void setWriteKey(String writeKey) {
        this.writeKey = writeKey;
    }
}
