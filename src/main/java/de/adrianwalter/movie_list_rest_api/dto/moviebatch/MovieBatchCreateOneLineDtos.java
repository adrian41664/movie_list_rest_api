package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieBatchCreateOneLineDtos extends MovieBatchCreateDtos< MovieBatchCreateOneLineDto >
        implements MovieBatchCreateSubTypeMarker {

    private final String type = "oneLine";
}
