package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public abstract class MovieBatchCreateDtos< T > implements MovieBatchCreateSubTypeMarker {

    @NotNull
    private long movieListId;

    @NotNull
    private List< T > movies;
}
