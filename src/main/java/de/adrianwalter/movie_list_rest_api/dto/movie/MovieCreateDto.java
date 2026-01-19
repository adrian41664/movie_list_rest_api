package de.adrianwalter.movie_list_rest_api.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public abstract class MovieCreateDto implements MovieCreateSubTypeMarker {


    @NotNull(message = "movieListId must be valid!")
    private long movieListId;

    @NotNull
    private int userRating;

    @NotBlank
    private String movieTitle;

    // String is nullable
    private String seenOn;

    // Integer is nullable
    private Integer releaseYear;

    // should not be null, but is required as null in some cases
    private LocalDate seenAt;

    public long getMovieListId() {
        return movieListId;
    }

    public void setMovieListId( long movieListId ) {
        this.movieListId = movieListId;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating( int userRating ) {
        this.userRating = userRating;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle( String movieTitle ) {
        this.movieTitle = movieTitle;
    }

    public String getSeenOn() {
        return seenOn;
    }

    public void setSeenOn( String seenOn ) {
        this.seenOn = seenOn;
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

}
