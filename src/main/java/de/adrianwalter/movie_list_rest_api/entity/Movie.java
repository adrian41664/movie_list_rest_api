package de.adrianwalter.movie_list_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Movie {

    //  > MOVIE_ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    //	> MOVIE_LIST_ID (FK)
    @ManyToOne
    @JoinColumn(name = "movie_list_id", nullable = false)
    private MovieList movieList;

    //	> USER_RATING
    @Column(nullable = false)
    private String userRating;

    //	> MOVIE_NAME
    @NotBlank
    @Column(unique = true)
    private String name;

    //	> RELEASE_YEAR {nullable}
    @Column(nullable = true)
    private int releaseYear;

    //	> ADDED TO LIST DATE TIME / Seen
    @Column(nullable = false)
    private LocalDateTime seenAt;

    //	> PLATFORM (SEEN ON) {nullable}
    @Column(nullable = true)
    private String seenOn;

    //	> USER NOTE {nullable, freies Textfeld für alle möglichen Infos des Users}
    @Column(nullable = true)
    private String userNote;

    // MovieDetail Instanz für optionale Infos
    @OneToOne
    private MovieDetail movieDetail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MovieList getMovieList() {
        return movieList;
    }

    public void setMovieList(MovieList movieList) {
        this.movieList = movieList;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public LocalDateTime getSeenAt() {
        return seenAt;
    }

    public void setSeenAt(LocalDateTime seenAt) {
        this.seenAt = seenAt;
    }

    public String getSeenOn() {
        return seenOn;
    }

    public void setSeenOn(String seenOn) {
        this.seenOn = seenOn;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public MovieDetail getMovieDetail() {
        return movieDetail;
    }

    public void setMovieDetail(MovieDetail movieDetail) {
        this.movieDetail = movieDetail;
    }

}
