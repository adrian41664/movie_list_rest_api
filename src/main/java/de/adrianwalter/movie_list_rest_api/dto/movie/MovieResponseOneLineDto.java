package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Data;

@Data
public class MovieResponseOneLineDto {


    private long movieId;

    private String movieInformation;


    public long getMovieId() {
        return movieId;
    }


    public void setMovieId( long movieId ) {
        this.movieId = movieId;
    }


    public String getMovieInformation() {
        return movieInformation;
    }


    public void setMovieInformation( String movieInformation ) {
        this.movieInformation = movieInformation;
    }
}
