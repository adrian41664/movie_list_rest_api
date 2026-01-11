package de.adrianwalter.movie_list_rest_api.dto.movie;

public class MovieCreateOneLineDto implements MovieCreateSubTypeMarker{

    private String type = "oneLine";


    long movieListId;
    String movieInformation;


    public long getMovieListId() {
        return movieListId;
    }


    public void setMovieListId( long movieListId ) {
        this.movieListId = movieListId;
    }


    public String getMovieInformation() {
        return movieInformation;
    }


    public void setMovieInformation( String movieInformation ) {
        this.movieInformation = movieInformation;
    }
}
