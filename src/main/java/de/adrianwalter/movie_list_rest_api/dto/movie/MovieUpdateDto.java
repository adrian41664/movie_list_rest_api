package de.adrianwalter.movie_list_rest_api.dto.movie;

import de.adrianwalter.movie_list_rest_api.entity.MovieDetail;
import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class MovieUpdateDto {

    //	> USER_RATING
    @Column( nullable = true )
    private Integer userRating;

    //	> MOVIE_NAME - must be non-unique, cause rating is tied to a single movie
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
    // @OneToOne( optional = true )
    // @JoinColumn( nullable = true )
    // private MovieDetail movieDetail;


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


    public String getGenre() {
        return genre;
    }


    public void setGenre( String genre ) {
        this.genre = genre;
    }

}
