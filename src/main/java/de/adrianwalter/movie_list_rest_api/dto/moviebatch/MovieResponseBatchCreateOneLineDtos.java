package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieResponseBatchCreateOneLineDtos extends MovieBatchCreateDtos< MovieResponseOneLineDto > {

    // @ToDo: Due to inheritance response is delivered with a "type"-field.

}
