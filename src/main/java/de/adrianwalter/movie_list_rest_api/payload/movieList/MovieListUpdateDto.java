package de.adrianwalter.movie_list_rest_api.payload.movieList;

public class MovieListUpdateDto {

    // Both can be blank; if blank: no update
    private String movieListName;
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
