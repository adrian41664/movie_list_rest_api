package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import lombok.Data;

@Data
public class MovieResponseBatchCreateOneLineDtos extends MovieBatchCreateDtos< MovieResponseOneLineDto> {

    // @ToDo: Due to inheritance response is delivered with a "type"-field.

}
