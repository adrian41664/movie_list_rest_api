package de.adrianwalter.movie_list_rest_api.sortandsearch;

import de.adrianwalter.movie_list_rest_api.entity.Movie;

@FunctionalInterface
public interface MovieFilter {

    // Predicate<T> / test
    boolean matches( Movie movie );

    default MovieFilter and( MovieFilter other ) {
        return movie -> this.matches( movie ) && other.matches( movie );
    }

    default MovieFilter or( MovieFilter other ) {
        return movie -> this.matches( movie ) || other.matches( movie );
    }
}
