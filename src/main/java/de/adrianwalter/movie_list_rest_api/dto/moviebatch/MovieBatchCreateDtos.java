package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import java.util.List;

public abstract class MovieBatchCreateDtos<T> implements MovieBatchCreateSubTypeMarker {

    private long movieListId;

    private List<T> movieCreateDtos;


    public long getMovieListId() {
        return movieListId;
    }


    public void setMovieListId( long movieListId ) {
        this.movieListId = movieListId;
    }


    public List< T > getMovieCreateDtos() {
        return movieCreateDtos;
    }


    public void setMovieCreateDtos( List< T > movieCreateDtos ) {
        this.movieCreateDtos = movieCreateDtos;
    }
}
