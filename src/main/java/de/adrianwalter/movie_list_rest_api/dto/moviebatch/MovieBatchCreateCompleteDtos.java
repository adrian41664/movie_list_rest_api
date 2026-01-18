package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

public class MovieBatchCreateCompleteDtos extends MovieBatchCreateDtos< MovieBatchCreateCompleteDto >
        implements MovieBatchCreateSubTypeMarker {

    private String type = "complete";
}
