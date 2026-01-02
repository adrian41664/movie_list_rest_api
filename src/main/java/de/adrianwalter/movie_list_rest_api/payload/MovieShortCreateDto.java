package de.adrianwalter.movie_list_rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieShortCreateDto {

    // toDo: Create more Versions (short, full, detailed)

    @NotNull(message = "movieListId must be valid!")
    private long movieListId;

    @NotBlank
    private String userRating;

    @NotBlank
    private String movieName;

    // String is nullable
    private String seenOn;

    // Integer is nullable
    private Integer releaseYear;

    // should not be null, but is required as null in special cases
    private LocalDate seenAt;

    public long getMovieListId() {
        return movieListId;
    }

    public void setMovieListId( long movieListId ) {
        this.movieListId = movieListId;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating( String userRating ) {
        this.userRating = userRating;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName( String movieName ) {
        this.movieName = movieName;
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
