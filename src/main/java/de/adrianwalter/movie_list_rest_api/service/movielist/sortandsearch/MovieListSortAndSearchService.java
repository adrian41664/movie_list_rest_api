package de.adrianwalter.movie_list_rest_api.service.movielist.sortandsearch;

import de.adrianwalter.movie_list_rest_api.entity.Movie;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MovieListSortAndSearchService {

    // sorting and filtering movies by params
    public List< Movie > filterAndSort(
            List< Movie > movies,
            MovieFilter filter,
            String sortBy,
            boolean ascending ) {

        Stream< Movie > movieStream = movies.stream();

        // apply filter
        if ( filter != null ) {

            movieStream = movieStream.filter( filter::matches );
        }

        // apply sorting
        if ( sortBy != null && !sortBy.isEmpty() ) {

            Comparator< Movie > comparator = getComparator( sortBy );

            if ( comparator != null ) {

                movieStream = movieStream.sorted( ascending ? comparator : comparator.reversed() );
            }
        }

        return movieStream.collect( Collectors.toList() );
    }


    // Overloaded "default" method
    public List< Movie > filterAndSort( List< Movie > movies, MovieFilter filter ) {

        return filterAndSort( movies, filter, "title", true );
    }


    // Comparator based on Movie field names
    private Comparator< Movie > getComparator( String sortBy ) {

        return switch ( sortBy.toLowerCase() ) {

            case "title" -> Comparator.comparing(
                    Movie::getMovieTitle,
                    Comparator.nullsLast( String.CASE_INSENSITIVE_ORDER )
            );
            case "rating", "user-rating" -> Comparator.comparing(
                    Movie::getUserRating,
                    Comparator.nullsLast( Comparator.naturalOrder() )
            );
            case "year", "release-year" -> Comparator.comparing(
                    Movie::getReleaseYear,
                    Comparator.nullsLast( Comparator.naturalOrder() )
            );
            case "genre" -> Comparator.comparing(
                    Movie::getGenre,
                    Comparator.nullsLast( String.CASE_INSENSITIVE_ORDER )
            );
            case "date" -> Comparator.comparing(
                    Movie::getSeenAt,
                    Comparator.nullsLast( Comparator.naturalOrder() )
            );
            default -> null;
        };
    }


    // combined filter built of params
    public MovieFilter buildFilter(
            String title, Boolean isRated, Integer minRating, Integer maxRating, Integer year, Integer minYear,
            Integer maxYear, String genre, String seenOn, String userNoteKeyword ) {

        // Start with "true"
        MovieFilter filter = movie -> true;

        if ( title != null && !title.isEmpty() ) {
            filter = filter.and( MovieFilters.byTitle( title ) );
        }
        if ( isRated != null ) {
            filter = filter.and( MovieFilters.isRated( isRated ) );
        }
        if ( minRating != null ) {
            filter = filter.and( MovieFilters.byMinRating( minRating ) );
        }
        if ( maxRating != null ) {
            filter = filter.and( MovieFilters.byMaxRating( maxRating ) );
        }
        if ( year != null ) {
            filter = filter.and( MovieFilters.byYear( year ) );
        }
        else if ( minYear != null || maxYear != null ) {
            filter = filter.and( MovieFilters.byYearRange( minYear, maxYear ) );
        }
        if ( genre != null && !genre.isEmpty() ) {
            filter = filter.and( MovieFilters.byGenre( genre ) );
        }
        if ( seenOn != null && !seenOn.isEmpty() ) {
            filter = filter.and( MovieFilters.byPlatform( seenOn ) );
        }
        if ( userNoteKeyword != null && !userNoteKeyword.isEmpty() ) {
            filter = filter.and( MovieFilters.byUserNoteKeyword( userNoteKeyword ) );
        }

        return filter;
    }


}
