package de.adrianwalter.movie_list_rest_api.sortandsearch;

public class MovieFilters {

    public static MovieFilter byTitle( String title ) {
        return movie -> movie.getMovieTitle() != null &&
                movie.getMovieTitle().toLowerCase().contains( title.toLowerCase() );
    }


    public static MovieFilter byUserNoteKeyword( String userNoteKeyword ) {
        return movie -> movie.getUserNote() != null &&
                movie.getUserNote().toLowerCase().contains( userNoteKeyword.toLowerCase() );
    }


    public static MovieFilter byGenre( String genre ) {
        return movie -> movie.getGenre() != null &&
                movie.getGenre().equalsIgnoreCase( genre );
    }


    public static MovieFilter byPlatform( String seenOn ) {
        return movie -> movie.getSeenOn() != null &&
                movie.getSeenOn().equalsIgnoreCase( seenOn );
    }


    public static MovieFilter byMinRating( int minRating ) {
        return movie -> movie.getUserRating() != null &&
                movie.getUserRating() >= minRating;
    }


    public static MovieFilter byMaxRating( int maxRating ) {
        return movie -> movie.getUserRating() != null &&
                movie.getUserRating() <= maxRating;
    }


    public static MovieFilter byYear( Integer year ) {
        return movie -> movie.getReleaseYear() != null &&
                movie.getReleaseYear().equals( year );
    }


    public static MovieFilter byYearRange( Integer minYear, Integer maxYear ) {
        return movie -> {
            if ( movie.getReleaseYear() == null ) return false;
            boolean afterMin = minYear == null || movie.getReleaseYear() >= minYear;
            boolean beforeMax = maxYear == null || movie.getReleaseYear() <= maxYear;

            return afterMin && beforeMax;
        };
    }


    public static MovieFilter isRated( Boolean isRated ) {

        // if: ( noFilter ( == isWatched = null )) OR userRating >= 0
        // IF no filter is applied ( == null ) OR movie has userRating >= 0

        return movie -> isRated == null ||
                ( movie.getUserRating() != null && movie.getUserRating() >= 0 );
    }
}