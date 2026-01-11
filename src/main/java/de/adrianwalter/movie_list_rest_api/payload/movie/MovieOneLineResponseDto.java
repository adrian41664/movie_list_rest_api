package de.adrianwalter.movie_list_rest_api.payload.movie;

import lombok.Data;

@Data
public class MovieOneLineResponseDto {

    String movieInformation;


    public String getMovieInformation() {
        return movieInformation;
    }


    public void setMovieInformation( String movieInformation ) {
        this.movieInformation = movieInformation;
    }
}
