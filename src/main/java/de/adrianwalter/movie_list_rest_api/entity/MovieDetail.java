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

    @OneToOne
    private Movie movie;

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

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public float getImdbScore() {
        return imdbScore;
    }

    public void setImdbScore(float imdbScore) {
        this.imdbScore = imdbScore;
    }

    public int getRottenTomatoesScore() {
        return rottenTomatoesScore;
    }

    public void setRottenTomatoesScore(int rottenTomatoesScore) {
        this.rottenTomatoesScore = rottenTomatoesScore;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getCastNames() {
        return castNames;
    }

    public void setCastNames(String castNames) {
        this.castNames = castNames;
    }
}
