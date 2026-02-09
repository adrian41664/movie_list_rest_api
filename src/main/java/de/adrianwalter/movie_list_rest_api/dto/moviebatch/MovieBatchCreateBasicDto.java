package de.adrianwalter.movie_list_rest_api.dto.moviebatch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Schema( description = "DTO for a single movie of a batch-creation of movies with basic details" )
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MovieBatchCreateBasicDto extends MovieBatchCreateDto {

}
