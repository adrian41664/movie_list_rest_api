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
    private long id;

    @NotBlank
    @Column(unique = true)
    private String userName;

    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL)
    private List<MovieList> movieLists;

    public List<MovieList> getMovieLists(){
        return movieLists;
    }

    public void setMovieLists( List<MovieList> movieLists ){
        this.movieLists = movieLists;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
