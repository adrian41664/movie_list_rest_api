package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MovieBatchCreateBasicDtos extends MovieBatchCreateDtos< MovieBatchCreateBasicDto >
        implements MovieBatchCreateSubTypeMarker {

    private String type = "basic";



    }
