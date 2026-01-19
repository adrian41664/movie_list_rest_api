package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import java.util.List;

public abstract class MovieBatchCreateDtos<T> implements MovieBatchCreateSubTypeMarker {

    private long movieListId;

    private List<T> movieTypes;


    public long getMovieListId() {
        return movieListId;
    }


    public void setMovieListId( long movieListId ) {
        this.movieListId = movieListId;
    }


    public List< T > getMovieTypes() {
        return movieTypes;
    }


    public void setMovieTypes( List< T > movieCreateDtos ) {
        this.movieTypes = movieCreateDtos;
    }
}
