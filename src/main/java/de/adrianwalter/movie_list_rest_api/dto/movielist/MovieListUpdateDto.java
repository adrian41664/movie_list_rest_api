package de.adrianwalter.movie_list_rest_api.dto.movielist;

import io.swagger.v3.oas.annotations.media.Schema;

public class MovieListUpdateDto {

    @Schema( description = "Unique name (per user) of the movie-list", example = "Movies last year" )
    private String movieListName;

    @Schema( description = "Description of the movie-list", example = "all movies watched at home last year" )
    private String movieListDescription;


    public String getMovieListName() {
        return movieListName;
    }


    public void setMovieListName( String movieListName ) {
        this.movieListName = movieListName;
    }


    public String getMovieListDescription() {
        return movieListDescription;
    }


    public void setMovieListDescription( String movieListDescription ) {
        this.movieListDescription = movieListDescription;
    }


}
