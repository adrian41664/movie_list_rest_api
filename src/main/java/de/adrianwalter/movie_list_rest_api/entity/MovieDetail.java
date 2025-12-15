package de.adrianwalter.movie_list_rest_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MovieDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    //	> SYNOPSIS {nullable}
    @Column(nullable = true)
    private String synopsis;

    //	> IMDB-RATING_SCORE {nullable}
    private float imdbScore;

    //	> RT-RATING_SCORE {nullable}
    private int rottenTomatoesScore;

    //	> IMDB-ID {nullable}
    @Column(nullable = true)
    private String imdbId;

    //	> DIRECTOR {nullable}
    @Column(nullable = true)
    private String directorName;

    //	> CAST_NAMES {nullable}
    @Column(nullable = true)
    private String castNames;

}
