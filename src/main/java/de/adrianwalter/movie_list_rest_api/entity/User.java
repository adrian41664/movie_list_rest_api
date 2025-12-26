package de.adrianwalter.movie_list_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long userId;

    @NotBlank
    @Column(unique = true)
    private String userName;

    // movieLists is a List of Lists containing Movie-Objects
    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL)
    private List<MovieList> movieLists;

    public List<MovieList> getMovieLists(){
        return movieLists;
    }

    // ToDo: Apply functionality
    private String readAndWriteKey;

    public String getReadAndWriteKey() {
        return readAndWriteKey;
    }

    public void setReadAndWriteKey(String readKey) {
        this.readAndWriteKey = readKey;
    }

    public void setMovieLists( List<MovieList> movieLists ){
        this.movieLists = movieLists;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
