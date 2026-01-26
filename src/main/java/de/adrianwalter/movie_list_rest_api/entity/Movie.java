package de.adrianwalter.movie_list_rest_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Movie {

    //  > MOVIE_ID (PK)
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id", unique = true )
    private long movieId;

    //	> MOVIE_LIST_ID (FK)
    @ManyToOne
    @JoinColumn( name = "movie_list_id", nullable = false )
    private MovieList movieList;

    //	> USER_RATING
    @Column( nullable = true )
    private Integer userRating;

    //	> MOVIE_NAME - must be non-unique, cause rating is tied to a single movie
    @NotBlank
    @Column( unique = false )
    private String movieTitle;

    //	> RELEASE_YEAR {nullable}
    @Column( nullable = true )
    private Integer releaseYear;

    //	> ADDED TO LIST DATE TIME / Seen
    @Column( nullable = true )
    private LocalDate seenAt;

    //	> PLATFORM (SEEN ON) {nullable}
    @Column( nullable = true )
    private String seenOn;

    //	> USER NOTE {nullable} text field for user
    @Column( nullable = true )
    private String userNote;

    @Column( nullable = true )
    private String genre;

    // MovieDetail - Instance for optional details
    @OneToOne( optional = true )
    @JoinColumn( nullable = true )
    private MovieDetail movieDetail;


    public long getMovieId() {
        return movieId;
    }


    public MovieList getMovieList() {
        return movieList;
    }


    public void setMovieList( MovieList movieList ) {
        this.movieList = movieList;
    }


    public Integer getUserRating() {
        return userRating;
    }


    public void setUserRating( Integer userRating ) {
        this.userRating = userRating;
    }


    public String getMovieTitle() {
        return movieTitle;
    }


    public void setMovieTitle( String movieTitle ) {
        this.movieTitle = movieTitle;
    }


    public Integer getReleaseYear() {
        return releaseYear;
    }


    public void setReleaseYear( Integer releaseYear ) {
        this.releaseYear = releaseYear;
    }


    public LocalDate getSeenAt() {
        return seenAt;
    }


    public void setSeenAt( LocalDate seenAt ) {
        this.seenAt = seenAt;
    }


    public String getSeenOn() {
        return seenOn;
    }


    public void setSeenOn( String seenOn ) {
        this.seenOn = seenOn;
    }


    public String getUserNote() {
        return userNote;
    }


    public void setUserNote( String userNote ) {
        this.userNote = userNote;
    }


    public MovieDetail getMovieDetail() {
        return movieDetail;
    }


    public void setMovieDetail( MovieDetail movieDetail ) {
        this.movieDetail = movieDetail;
    }


    public String getGenre() {
        return genre;
    }


    public void setGenre( String genre ) {
        this.genre = genre;
    }

}
