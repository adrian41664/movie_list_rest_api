package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public abstract class MovieBatchCreateDto {

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
