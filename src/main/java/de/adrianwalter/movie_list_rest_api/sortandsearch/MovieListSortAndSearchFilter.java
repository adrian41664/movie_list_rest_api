package de.adrianwalter.movie_list_rest_api.sortandsearch;

import org.springframework.web.bind.annotation.RequestParam;

public class MovieListSortAndSearchFilter {

    String title;
    int rating;
    int releaseYear ;
    String seenOn;
    String userNote;
    String genre;
    String sortBy;
    String sortDirection;

}
