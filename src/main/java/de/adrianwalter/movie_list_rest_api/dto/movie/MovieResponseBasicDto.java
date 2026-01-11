package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieResponseBasicDto {

    private Long movieId;

    private String movieName;

    private int userRating;
    private int releaseYear;
    private String seenOn;
    private String userNote;
    private String genre;
    private LocalDate seenAt;


    public Long getMovieId() {
        return movieId;
    }


    public void setMovieId( Long movieId ) {
        this.movieId = movieId;
    }


    public String getMovieName() {
        return movieName;
    }


    public void setMovieName( String movieName ) {
        this.movieName = movieName;
    }


    public int getUserRating() {
        return userRating;
    }


    public void setUserRating( int userRating ) {
        this.userRating = userRating;
    }


    public int getReleaseYear() {
        return releaseYear;
    }


    public void setReleaseYear( int releaseYear ) {
        this.releaseYear = releaseYear;
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


    public LocalDate getSeenAt() {
        return seenAt;
    }


    public void setSeenAt( LocalDate seenAt ) {
        this.seenAt = seenAt;
    }


}
