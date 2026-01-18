package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import lombok.Data;

@Data
public class MovieBatchCreateOneLineDtos extends MovieBatchCreateDtos<MovieBatchCreateOneLineDto>
        implements MovieBatchCreateSubTypeMarker {

    private String type = "oneLine";


}
