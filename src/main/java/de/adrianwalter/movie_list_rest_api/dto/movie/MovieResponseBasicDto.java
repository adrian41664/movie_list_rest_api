package de.adrianwalter.movie_list_rest_api.dto.movie;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieResponseBasicDto {

    private Long movieId;

    private String movieTitle;

    private int userRating;
    private Integer releaseYear;
    private String seenOn;
    private String userNote;
    private String genre;
    private LocalDate seenAt;

}
