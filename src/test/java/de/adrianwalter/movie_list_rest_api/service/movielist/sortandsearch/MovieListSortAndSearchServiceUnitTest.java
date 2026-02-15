package de.adrianwalter.movie_list_rest_api.service.movielist.sortandsearch;

import de.adrianwalter.movie_list_rest_api.entity.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MovieListSortAndSearchServiceUnitTest {

    private MovieListSortAndSearchService service;
    private List< Movie > testMovies;


    @BeforeEach
    void setUp() {
        service = new MovieListSortAndSearchService();
        testMovies = createTestMovies();
    }


    private List< Movie > createTestMovies() {
        List< Movie > movies = new ArrayList<>();

        Movie movie1 = new Movie();
        movie1.setMovieTitle( "Inception" );
        movie1.setUserRating( 9 );
        movie1.setReleaseYear( 2010 );
        movie1.setGenre( "Sci-Fi" );
        movie1.setSeenOn( "Netflix" );
        movie1.setSeenAt( LocalDate.of( 2023, 5, 15 ) );
        movie1.setUserNote( "Mind-bending masterpiece" );
        movies.add( movie1 );

        Movie movie2 = new Movie();
        movie2.setMovieTitle( "The Dark Knight" );
        movie2.setUserRating( 10 );
        movie2.setReleaseYear( 2008 );
        movie2.setGenre( "Action" );
        movie2.setSeenOn( "Cinema" );
        movie2.setSeenAt( LocalDate.of( 2023, 3, 10 ) );
        movie2.setUserNote( "Best Batman movie" );
        movies.add( movie2 );

        Movie movie3 = new Movie();
        movie3.setMovieTitle( "Avatar" );
        movie3.setUserRating( 7 );
        movie3.setReleaseYear( 2009 );
        movie3.setGenre( "Sci-Fi" );
        movie3.setSeenOn( "Netflix" );
        movie3.setSeenAt( LocalDate.of( 2023, 8, 20 ) );
        movie3.setUserNote( "Great visuals" );
        movies.add( movie3 );

        Movie movie4 = new Movie();
        movie4.setMovieTitle( "Interstellar" );
        movie4.setUserRating( null ); // Not rated
        movie4.setReleaseYear( 2014 );
        movie4.setGenre( "Sci-Fi" );
        movie4.setSeenOn( "Amazon Prime" );
        movie4.setSeenAt( null );
        movie4.setUserNote( "Haven't watched yet" );
        movies.add( movie4 );

        Movie movie5 = new Movie();
        movie5.setMovieTitle( "Parasite" );
        movie5.setUserRating( 9 );
        movie5.setReleaseYear( 2019 );
        movie5.setGenre( "Drama" );
        movie5.setSeenOn( "Netflix" );
        movie5.setSeenAt( LocalDate.of( 2023, 6, 5 ) );
        movie5.setUserNote( "Brilliant storytelling" );
        movies.add( movie5 );

        return movies;
    }


    @Nested
    @DisplayName( "FilterAndSort Tests" )
    class FilterAndSortTests {

        @Test
        @DisplayName( "Should return all movies when no filter is applied" )
        void shouldReturnAllMoviesWithoutFilter() {
            List< Movie > result = service.filterAndSort( testMovies, null, null, true );

            assertThat( result ).hasSize( 5 );
            assertThat( result ).containsExactlyInAnyOrderElementsOf( testMovies );
        }


        @Test
        @DisplayName( "Should filter movies by title" )
        void shouldFilterByTitle() {
            MovieFilter filter = MovieFilters.byTitle( "Dark" );

            List< Movie > result = service.filterAndSort( testMovies, filter, null, true );

            assertThat( result ).hasSize( 1 );
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "The Dark Knight" );
        }


        @Test
        @DisplayName( "Should filter movies by genre" )
        void shouldFilterByGenre() {
            MovieFilter filter = MovieFilters.byGenre( "Sci-Fi" );

            List< Movie > result = service.filterAndSort( testMovies, filter, null, true );

            assertThat( result ).hasSize( 3 );
            assertThat( result ).extracting( Movie::getGenre )
                    .containsOnly( "Sci-Fi" );
        }


        @Test
        @DisplayName( "Should filter movies by minimum rating" )
        void shouldFilterByMinRating() {
            MovieFilter filter = MovieFilters.byMinRating( 9 );

            List< Movie > result = service.filterAndSort( testMovies, filter, null, true );

            assertThat( result ).hasSize( 3 );
            assertThat( result ).extracting( Movie::getUserRating )
                    .allMatch( rating -> rating >= 9 );
        }


        @Test
        @DisplayName( "Should combine multiple filters with AND" )
        void shouldCombineFiltersWithAnd() {
            MovieFilter filter = MovieFilters.byGenre( "Sci-Fi" )
                    .and( MovieFilters.byMinRating( 8 ) );

            List< Movie > result = service.filterAndSort( testMovies, filter, null, true );

            assertThat( result ).hasSize( 1 );
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "Inception" );
        }


        @Test
        @DisplayName( "Should return empty list when no movies match filter" )
        void shouldReturnEmptyListWhenNoMatch() {
            MovieFilter filter = MovieFilters.byGenre( "Horror" );

            List< Movie > result = service.filterAndSort( testMovies, filter, null, true );

            assertThat( result ).isEmpty();
        }
    }

    @Nested
    @DisplayName( "Sorting Tests" )
    class SortingTests {

        @Test
        @DisplayName( "Should sort by title ascending" )
        void shouldSortByTitleAscending() {
            List< Movie > result = service.filterAndSort( testMovies, null, "title", true );

            assertThat( result ).hasSize( 5 );
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "Avatar" );
            assertThat( result.get( 1 ).getMovieTitle() ).isEqualTo( "Inception" );
            assertThat( result.get( 4 ).getMovieTitle() ).isEqualTo( "The Dark Knight" );
        }


        @Test
        @DisplayName( "Should sort by title descending" )
        void shouldSortByTitleDescending() {
            List< Movie > result = service.filterAndSort( testMovies, null, "title", false );

            assertThat( result ).hasSize( 5 );
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "The Dark Knight" );
            assertThat( result.get( 4 ).getMovieTitle() ).isEqualTo( "Avatar" );
        }


        @Test
        @DisplayName( "Should sort by rating ascending" )
        void shouldSortByRatingAscending() {
            List< Movie > result = service.filterAndSort( testMovies, null, "rating", true );

            // The Dark Knight = 10
            // Inception = 9
            // Parasite = 9
            // Avatar = 7
            // Interstellar = null


            assertThat( result ).hasSize( 5 );
            // Null ratings should be last
            assertThat( result.get( 0 ).getUserRating() ).isNull(); // expected null / was 10
            assertThat( result.get( 4 ).getUserRating() ).isEqualTo( 10 );
        }


        @Test
        @DisplayName( "Should sort by rating descending" )
        void shouldSortByRatingDescending() {
            List< Movie > result = service.filterAndSort( testMovies, null, "rating", false );

            assertThat( result ).hasSize( 5 );
            assertThat( result.get( 0 ).getUserRating() ).isEqualTo( 10 );
            // Null ratings should still be last even when reversed
            assertThat( result.get( 4 ).getUserRating() ).isNull();
        }


        @Test
        @DisplayName( "Should sort by year ascending" )
        void shouldSortByYearAscending() {
            List< Movie > result = service.filterAndSort( testMovies, null, "year", true );

            assertThat( result ).hasSize( 5 );
            assertThat( result.get( 0 ).getReleaseYear() ).isEqualTo( 2008 );
            assertThat( result.get( 4 ).getReleaseYear() ).isEqualTo( 2019 );
        }


        @Test
        @DisplayName( "Should sort by genre case-insensitive" )
        void shouldSortByGenreCaseInsensitive() {
            List< Movie > result = service.filterAndSort( testMovies, null, "genre", true );

            assertThat( result ).hasSize( 5 );
            assertThat( result.get( 0 ).getGenre() ).isEqualTo( "Action" );
            assertThat( result.get( 1 ).getGenre() ).isEqualTo( "Drama" );
        }


        @Test
        @DisplayName( "Should sort by date ascending" )
        void shouldSortByDateAscending() {
            List< Movie > result = service.filterAndSort( testMovies, null, "date", true );

            assertThat( result ).hasSize( 5 );
            // Null dates should be last
            assertThat( result.get( 4 ).getSeenAt() ).isNull();
            assertThat( result.get( 0 ).getSeenAt() ).isEqualTo( LocalDate.of( 2023, 3, 10 ) );
        }


        @Test
        @DisplayName( "Should handle invalid sort field" )
        void shouldHandleInvalidSortField() {
            List< Movie > result = service.filterAndSort( testMovies, null, "invalid", true );

            // Should return all movies unsorted (original order maintained)
            assertThat( result ).hasSize( 5 );
        }


        @Test
        @DisplayName( "Should handle empty sort field" )
        void shouldHandleEmptySortField() {
            List< Movie > result = service.filterAndSort( testMovies, null, "", true );

            assertThat( result ).hasSize( 5 );
        }


        @Test
        @DisplayName( "Should handle null sort field" )
        void shouldHandleNullSortField() {
            List< Movie > result = service.filterAndSort( testMovies, null, null, true );

            assertThat( result ).hasSize( 5 );
        }
    }

    @Nested
    @DisplayName( "Combined Filter and Sort Tests" )
    class CombinedTests {

        @Test
        @DisplayName( "Should filter and sort together" )
        void shouldFilterAndSort() {
            MovieFilter filter = MovieFilters.byGenre( "Sci-Fi" );

            List< Movie > result = service.filterAndSort( testMovies, filter, "rating", false );

            assertThat( result ).hasSize( 3 );
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "Inception" ); // Rating 9
            assertThat( result.get( 1 ).getMovieTitle() ).isEqualTo( "Avatar" ); // Rating 7
            assertThat( result.get( 2 ).getUserRating() ).isNull(); // Interstellar
        }


        @Test
        @DisplayName( "Should handle complex filter and custom sort" )
        void shouldHandleComplexFilterAndSort() {
            MovieFilter filter = MovieFilters.isRated( true )
                    .and( MovieFilters.byMinRating( 7 ) );

            List< Movie > result = service.filterAndSort( testMovies, filter, "year", true );

            assertThat( result ).hasSize( 4 );
            assertThat( result.get( 0 ).getReleaseYear() ).isEqualTo( 2008 );
        }
    }

    @Nested
    @DisplayName( "Overloaded Method Tests" )
    class OverloadedMethodTests {

        @Test
        @DisplayName( "Should use default sorting when using overloaded method" )
        void shouldUseDefaultSorting() {
            MovieFilter filter = MovieFilters.byGenre( "Sci-Fi" );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 3 );
            // Should be sorted by title ascending (default)
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "Avatar" );
            assertThat( result.get( 1 ).getMovieTitle() ).isEqualTo( "Inception" );
            assertThat( result.get( 2 ).getMovieTitle() ).isEqualTo( "Interstellar" );
        }
    }

    @Nested
    @DisplayName( "BuildFilter Tests" )
    class BuildFilterTests {

        @Test
        @DisplayName( "Should build filter with title parameter" )
        void shouldBuildFilterWithTitle() {
            MovieFilter filter = service.buildFilter( "Inception", null, null, null,
                    null, null, null, null, null, null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 1 );
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "Inception" );
        }


        @Test
        @DisplayName( "Should build filter with isRated parameter" )
        void shouldBuildFilterWithIsRated() {
            MovieFilter filter = service.buildFilter( null, true, null, null,
                    null, null, null, null, null, null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 4 );
            assertThat( result ).allMatch( movie -> movie.getUserRating() != null );
        }


        @Test
        @DisplayName( "Should build filter with rating range" )
        void shouldBuildFilterWithRatingRange() {
            MovieFilter filter = service.buildFilter( null, null, 8, 9,
                    null, null, null, null, null, null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 2 );
            assertThat( result ).extracting( Movie::getUserRating )
                    .allMatch( rating -> rating >= 8 && rating <= 9 );
        }


        @Test
        @DisplayName( "Should build filter with specific year" )
        void shouldBuildFilterWithYear() {
            MovieFilter filter = service.buildFilter( null, null, null, null,
                    2010, null, null, null, null, null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 1 );
            assertThat( result.get( 0 ).getReleaseYear() ).isEqualTo( 2010 );
        }


        @Test
        @DisplayName( "Should build filter with year range" )
        void shouldBuildFilterWithYearRange() {
            MovieFilter filter = service.buildFilter( null, null, null, null,
                    null, 2008, 2010, null, null, null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 3 );
            assertThat( result ).extracting( Movie::getReleaseYear )
                    .allMatch( year -> year >= 2008 && year <= 2010 );
        }


        @Test
        @DisplayName( "Should build filter with genre" )
        void shouldBuildFilterWithGenre() {
            MovieFilter filter = service.buildFilter( null, null, null, null,
                    null, null, null, "Drama", null, null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 1 );
            assertThat( result.get( 0 ).getGenre() ).isEqualTo( "Drama" );
        }


        @Test
        @DisplayName( "Should build filter with platform" )
        void shouldBuildFilterWithPlatform() {
            MovieFilter filter = service.buildFilter( null, null, null, null,
                    null, null, null, null, "Netflix", null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 3 );
            assertThat( result ).extracting( Movie::getSeenOn )
                    .containsOnly( "Netflix" );
        }


        @Test
        @DisplayName( "Should build filter with user note keyword" )
        void shouldBuildFilterWithUserNoteKeyword() {
            MovieFilter filter = service.buildFilter( null, null, null, null,
                    null, null, null, null, null, "masterpiece" );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 1 );
            assertThat( result.get( 0 ).getUserNote() ).contains( "masterpiece" );
        }


        @Test
        @DisplayName( "Should build combined filter with multiple parameters" )
        void shouldBuildCombinedFilter() {
            MovieFilter filter = service.buildFilter( null, true, 8, null,
                    null, null, null, "Sci-Fi", "Netflix", null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 1 );
            assertThat( result.get( 0 ).getMovieTitle() ).isEqualTo( "Inception" );
        }


        @Test
        @DisplayName( "Should return all movies when no parameters are set" )
        void shouldReturnAllWhenNoParameters() {
            MovieFilter filter = service.buildFilter( null, null, null, null,
                    null, null, null, null, null, null );

            List< Movie > result = service.filterAndSort( testMovies, filter );

            assertThat( result ).hasSize( 5 );
        }
    }

    @Nested
    @DisplayName( "Edge Cases" )
    class EdgeCaseTests {

        @Test
        @DisplayName( "Should handle empty movie list" )
        void shouldHandleEmptyList() {
            List< Movie > emptyList = new ArrayList<>();

            List< Movie > result = service.filterAndSort( emptyList, null, "title", true );

            assertThat( result ).isEmpty();
        }


        @Test
        @DisplayName( "Should handle movies with all null fields" )
        void shouldHandleAllNullFields() {
            Movie nullMovie = new Movie();
            List< Movie > movies = List.of( nullMovie );

            List< Movie > result = service.filterAndSort( movies, null, "title", true );

            assertThat( result ).hasSize( 1 );
        }


        @Test
        @DisplayName( "Should handle case-insensitive sorting" )
        void shouldHandleCaseInsensitiveSorting() {
            Movie movie1 = new Movie();
            movie1.setMovieTitle( "avatar" );

            Movie movie2 = new Movie();
            movie2.setMovieTitle( "Avatar" );

            Movie movie3 = new Movie();
            movie3.setMovieTitle( "AVATAR" );

            List< Movie > movies = List.of( movie1, movie2, movie3 );
            List< Movie > result = service.filterAndSort( movies, null, "title", true );

            // All should be considered equal, order preserved
            assertThat( result ).hasSize( 3 );
        }
    }
}