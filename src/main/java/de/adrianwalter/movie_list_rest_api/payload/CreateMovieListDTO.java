package de.adrianwalter.movie_list_rest_api.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMovieListDTO {

    @NotBlank
    private String movieListName;

    public void setMovieListName(String movieListName) {
        this.movieListName = movieListName;
    }

    public String getMovieListName() {
        return this.movieListName;
    }

}
