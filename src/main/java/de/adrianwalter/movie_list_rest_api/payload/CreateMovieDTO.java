package de.adrianwalter.movie_list_rest_api.payload;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateMovieDTO {

    @NotBlank

    private String movieName;

    private MovieList movieList;

    private String userRating;

    // nullable
    private int releaseYear;

    private LocalDateTime seenAt;

    // nullable
    private String seenOn;



    public void setMovieName(String movieListName) {
        this.movieName = movieListName;
    }

    public String getMovieName() {
        return this.movieName;
    }

}
