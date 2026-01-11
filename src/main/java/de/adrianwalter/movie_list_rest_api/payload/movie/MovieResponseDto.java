package de.adrianwalter.movie_list_rest_api.payload.movie;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieResponseDto {

    private Long movieId;

    private String movieName;

    private int userRating;
    private int releaseYear;
    private String genre;
    private String seenOn;
    private LocalDate seenAt;
    private String userNote;

    private Long movieListId;
    private String movieListName;

    private Long userId;
    private String userName;


    public MovieResponseDto() {
    }


    public String getGenre() {
        return genre;
    }


    public void setGenre( String genre ) {
        this.genre = genre;
    }


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


    public Long getMovieListId() {
        return movieListId;
    }


    public void setMovieListId( Long movieListId ) {
        this.movieListId = movieListId;
    }


    public String getMovieListName() {
        return movieListName;
    }


    public void setMovieListName( String movieListName ) {
        this.movieListName = movieListName;
    }


    public Long getUserId() {
        return userId;
    }


    public void setUserId( Long userId ) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName( String userName ) {
        this.userName = userName;
    }
}
