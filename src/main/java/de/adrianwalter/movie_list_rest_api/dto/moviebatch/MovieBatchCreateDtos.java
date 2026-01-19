package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public abstract class MovieBatchCreateDtos<T> implements MovieBatchCreateSubTypeMarker {

    @NotNull
    private long movieListId;

    @NotNull
    private List<T> movies;


    public long getMovieListId() {
        return movieListId;
    }


    public void setMovieListId( long movieListId ) {
        this.movieListId = movieListId;
    }


    public List< T > getMovies() {
        return movies;
    }


    public void setMovies( List< T > movieCreateDtos ) {
        this.movies = movieCreateDtos;
    }
}
