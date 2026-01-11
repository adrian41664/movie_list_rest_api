package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Data;

@Data
public class MovieResponseOneLineDto {

    String movieInformation;


    public String getMovieInformation() {
        return movieInformation;
    }


    public void setMovieInformation( String movieInformation ) {
        this.movieInformation = movieInformation;
    }
}
