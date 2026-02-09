package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import de.adrianwalter.movie_list_rest_api.dto.movie.MovieResponseOneLineDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO for the response of a batch-creation of movies in a compact and readable way " )
@Getter
@Setter
@EqualsAndHashCode( callSuper = true )
public class MovieResponseBatchCreateOneLineDtos extends MovieBatchCreateDtos< MovieResponseOneLineDto > {

    // @ToDo: Due to inheritance response is delivered with a "type"-field.

}
