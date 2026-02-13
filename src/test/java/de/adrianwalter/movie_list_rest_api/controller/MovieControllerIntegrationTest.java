package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.movie.*;
import de.adrianwalter.movie_list_rest_api.dto.moviebatch.*;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateByUserIdBodyDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.repository.MovieListRepository;
import de.adrianwalter.movie_list_rest_api.repository.MovieRepository;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.ANY )
@ActiveProfiles( "test" )
@DisplayName( "MovieController Integration Tests" )
class MovieControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieListRepository movieListRepository;

    @Autowired
    private MovieRepository movieRepository;


    @BeforeEach
    void cleanUp() {
        // clean up, before every test
        movieRepository.deleteAll();
        movieListRepository.deleteAll();
        userRepository.deleteAll();
    }


    // Helper method to create user and movie-list
    private MovieListMovieOneLineResponseDto createUserAndMovieList( ) {
        UserCreateDto userRequest = new UserCreateDto();
        userRequest.setUserName( "User123" );
        UserResponseShortDto user = restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

        MovieListCreateByUserIdBodyDto movieListRequest = new MovieListCreateByUserIdBodyDto();
        movieListRequest.setMovieListName( "My Movie List" );
        movieListRequest.setUserId( user.getUserId() );

        return restTemplate.postForObject( "/movie-lists", movieListRequest, MovieListMovieOneLineResponseDto.class );
    }


    // =========================================================
    // POST /movies - Create a new Movie
    // =========================================================


    @Nested
    @DisplayName( "POST /movies - Create Movie" )
    class CreateMovieTests {

        @Test
        @DisplayName( "Should create a new movie with basic type | return 200" )
        void createMovie_whenValidBasicType_shouldReturnSuccessCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto request = new MovieCreateBasicDto();
            request.setMovieListId( movieList.getMovieListId() );
            request.setMovieTitle( "The Matrix" );
            request.setReleaseYear( 1999 );

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response =
                    restTemplate.postForEntity( "/movies", request, MovieResponseBasicFullOwnershipDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieId() ).isNotNull();
            assertThat( response.getBody().getMovieTitle() ).isEqualTo( "The Matrix" );
            assertThat( response.getBody().getReleaseYear() ).isEqualTo( 1999 );
        }

        @Test
        @DisplayName( "Should create a new movie with complete type | return 200" )
        void createMovie_whenValidCompleteType_shouldReturnSuccessCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateCompleteDto request = new MovieCreateCompleteDto();
            request.setMovieListId( movieList.getMovieListId() );
            request.setMovieTitle( "Inception" );
            request.setReleaseYear( 2010 );
            request.setGenre( "Sci-Fi" );
            request.setUserRating( 9 );
            request.setSeenOn( "Netflix" );
            request.setUserNote( "Mind-bending" );
            request.setSeenAt( LocalDate.of( 2024, 1, 15 ) );

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response =
                    restTemplate.postForEntity( "/movies", request, MovieResponseBasicFullOwnershipDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieId() ).isNotNull();
            assertThat( response.getBody().getMovieTitle() ).isEqualTo( "Inception" );
            assertThat( response.getBody().getGenre() ).isEqualTo( "Sci-Fi".toUpperCase() );
            assertThat( response.getBody().getUserRating() ).isEqualTo( 9 );
        }

        @Test
        @DisplayName( "Should create a new movie with oneLine type | return 200" )
        void createMovie_whenValidOneLineType_shouldReturnSuccessCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateOneLineDto request = new MovieCreateOneLineDto();
            request.setMovieListId( movieList.getMovieListId() );
            request.setMovieInformation( "8 / The Shawshank Redemption / 1994 / Drama / Netflix / Amazing movie / 2024-02-10" );

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response =
                    restTemplate.postForEntity( "/movies", request, MovieResponseBasicFullOwnershipDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieId() ).isNotNull();
        }

        @Test
        @DisplayName( "Movie name already exists in movie-list | return code 409" )
        void createMovie_whenNameInUse_shouldReturnConflictCode() {

            // Arrange - create user, movie-list and first movie
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto firstRequest = new MovieCreateBasicDto();
            firstRequest.setMovieListId( movieList.getMovieListId() );
            firstRequest.setMovieTitle( "The Matrix" );
            firstRequest.setReleaseYear( 1999 );
            restTemplate.postForEntity( "/movies", firstRequest, MovieResponseBasicFullOwnershipDto.class );

            // Act - try to create second movie with same name
            MovieCreateBasicDto duplicateRequest = new MovieCreateBasicDto();
            duplicateRequest.setMovieListId( movieList.getMovieListId() );
            duplicateRequest.setMovieTitle( "The Matrix" );
            duplicateRequest.setReleaseYear( 1999 );

            ResponseEntity< String > response =
                    restTemplate.postForEntity( "/movies", duplicateRequest, String.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CONFLICT );
        }

        @Test
        @DisplayName( "Movie title is blank | return code 400" )
        void createMovie_whenTitleIsBlank_shouldReturnBadRequestCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto request = new MovieCreateBasicDto();
            request.setMovieListId( movieList.getMovieListId() );
            request.setMovieTitle( "" );
            request.setReleaseYear( 1999 );

            // Act
            ResponseEntity< String > response =
                    restTemplate.postForEntity( "/movies", request, String.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
        }

        @Test
        @DisplayName( "Movie-list not found | return code 404" )
        void createMovie_whenMovieListNotFound_shouldReturnNotFoundCode() {

            // Arrange - create no movie-list
            MovieCreateBasicDto request = new MovieCreateBasicDto();
            request.setMovieListId( 99999L );
            request.setMovieTitle( "The Matrix" );
            request.setReleaseYear( 1999 );

            // Act
            ResponseEntity< String > response =
                    restTemplate.postForEntity( "/movies", request, String.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

    }


    // =========================================================
    // POST /movies/batch - Create a batch of new Movies
    // =========================================================


    @Nested
    @DisplayName( "POST /movies/batch - Create Movie Batch" )
    class CreateMovieBatchTests {

        @Test
        @DisplayName( "Should create a batch of movies with basic type | return 200" )
        void createMovieBatch_whenValidByBasicType_shouldReturnSuccessCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            // create batch request with two movies (by basic)

            MovieBatchCreateBasicDtos batchRequest = new MovieBatchCreateBasicDtos();
            batchRequest.setMovieListId(  movieList.getMovieListId() );

            List<MovieBatchCreateBasicDto> batchList = new ArrayList<>();

            MovieBatchCreateBasicDto batchMovie1 = new MovieBatchCreateBasicDto();
            batchMovie1.setMovieTitle( "The Matrix" );
            batchMovie1.setReleaseYear( 1999 );

            batchList.add( batchMovie1 );

            MovieBatchCreateBasicDto batchMovie2 = new MovieBatchCreateBasicDto();
            batchMovie2.setMovieTitle( "Inception" );
            batchMovie2.setReleaseYear( 2010 );

            batchList.add( batchMovie2 );

            batchRequest.setMovies( batchList );

            // Act
            ResponseEntity< MovieResponseBatchCreateOneLineDtos > response =
                    restTemplate.postForEntity( "/movies/batch", batchRequest, MovieResponseBatchCreateOneLineDtos.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovies().size() ).isEqualTo( 2 );

        }

        @Test
        @DisplayName( "Should create a batch of movies with oneLine type | return 200" )
        void createMovieBatch_whenValidOneLineType_shouldReturnSuccessCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            // create batch request with two movies (by oneLine)

            MovieBatchCreateOneLineDtos batchRequest = new MovieBatchCreateOneLineDtos();
            batchRequest.setMovieListId(  movieList.getMovieListId() );


            List<MovieBatchCreateOneLineDto> batchList = new ArrayList<>();

            MovieBatchCreateOneLineDto batchMovie1 = new MovieBatchCreateOneLineDto();
            batchMovie1.setMovieInformation( "8 / The Matrix / 1999 / Sci-Fi / Netflix / Great movie / 2024-01-15" );

            batchList.add( batchMovie1 );

            MovieBatchCreateOneLineDto batchMovie2 = new MovieBatchCreateOneLineDto();
            batchMovie2.setMovieInformation( "9 / Inception / 2010 / Thriller / Prime / Mind-bending / 2024-02-20" );

            batchList.add( batchMovie2 );

            batchRequest.setMovies( batchList );


            // Act
            ResponseEntity< MovieResponseBatchCreateOneLineDtos > response =
                    restTemplate.postForEntity( "/movies/batch", batchRequest, MovieResponseBatchCreateOneLineDtos.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovies().size() ).isEqualTo( 2 );

        }

        @Test
        @DisplayName( "One movie name already exists in batch | return code 409" )
        void createMovieBatch_whenOneNameInUse_shouldReturnConflictCode() {

            // Arrange - create user, movie-list and first movie
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto firstMovie = new MovieCreateBasicDto();
            firstMovie.setMovieListId( movieList.getMovieListId() );
            firstMovie.setMovieTitle( "The Matrix" );
            firstMovie.setReleaseYear( 1999 );
            restTemplate.postForEntity( "/movies", firstMovie, MovieResponseBasicFullOwnershipDto.class );

            // Act - try to create batch with duplicate name
            MovieBatchCreateOneLineDtos batchRequest = new MovieBatchCreateOneLineDtos();
            batchRequest.setMovieListId( movieList.getMovieListId() );

            List<MovieBatchCreateOneLineDto> batchList = new ArrayList<>();

            MovieBatchCreateOneLineDto batchMovie1 = new MovieBatchCreateOneLineDto();
            batchMovie1.setMovieInformation( "8 / The Matrix / 1999 / Sci-Fi / Netflix / Great movie / 2024-01-15" );

            batchList.add( batchMovie1 );

            MovieBatchCreateOneLineDto batchMovie2 = new MovieBatchCreateOneLineDto();
            batchMovie2.setMovieInformation( "9 / Inception / 2010 / Thriller / Prime / Mind-bending / 2024-02-20" );

            batchList.add( batchMovie2 );

            batchRequest.setMovies( batchList );

            ResponseEntity< String > response =
                    restTemplate.postForEntity( "/movies/batch", batchRequest, String.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CONFLICT );
        }

        @Test
        @DisplayName( "One movie has incorrect syntax | return code 400" )
        void createMovieBatch_whenOneMovieWithIncorrectSyntax_shouldReturnBadRequestCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            // Act - create batch
            MovieBatchCreateOneLineDtos batchRequest = new MovieBatchCreateOneLineDtos();
            batchRequest.setMovieListId(  movieList.getMovieListId() );

            List<MovieBatchCreateOneLineDto> batchList = new ArrayList<>();

            MovieBatchCreateOneLineDto batchMovie1 = new MovieBatchCreateOneLineDto();
            batchMovie1.setMovieInformation( "8 / The Matrix / 1999 / Sci-Fi / Netflix / Great movie / 2024-01-15" );

            batchList.add( batchMovie1 );

            // create batch with incorrect syntax
            MovieBatchCreateOneLineDto batchMovie2 = new MovieBatchCreateOneLineDto();
            batchMovie2.setMovieInformation( "9 / Inception / 2010 / Thriller / Prime / Mind-bending " );

            batchList.add( batchMovie2 );

            batchRequest.setMovies( batchList );

            ResponseEntity< String > response =
                    restTemplate.postForEntity( "/movies/batch", batchRequest, String.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
        }

        @Test
        @DisplayName( "Empty batch | return code 400" )
        void createMovieBatch_whenEmptyBatchList_shouldReturnBadRequestCode() {

            // Arrange - create user and movie-list
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            // create batch with empty list
            MovieBatchCreateOneLineDtos batchRequest = new MovieBatchCreateOneLineDtos();
            batchRequest.setMovieListId(  movieList.getMovieListId() );

            List<MovieBatchCreateOneLineDto> batchList = new ArrayList<>();

            batchRequest.setMovies( batchList );

            // Act
            ResponseEntity< String > response =
                    restTemplate.postForEntity( "/movies/batch", batchRequest, String.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
        }

    }


    // =========================================================
    // GET /movies/{movieId} - Retrieve single movie
    // =========================================================

    @Nested
    @DisplayName( "GET /movies/{movieId} - Get Movie by ID" )
    class GetMovieTests {

        @Test
        @DisplayName( "Return movie successful | return code 200" )
        void getMovie_whenMovieIdIsValid_shouldReturnOkCode() {

            // Arrange - create user, movie-list and movie
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto createRequest = new MovieCreateBasicDto();
            createRequest.setMovieListId( movieList.getMovieListId() );
            createRequest.setMovieTitle( "The Matrix" );
            createRequest.setReleaseYear( 1999 );
            MovieResponseBasicFullOwnershipDto created =
                    restTemplate.postForObject( "/movies", createRequest, MovieResponseBasicFullOwnershipDto.class );

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response =
                    restTemplate.getForEntity( "/movies/" + created.getMovieId(),
                            MovieResponseBasicFullOwnershipDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieId() ).isEqualTo( created.getMovieId() );
            assertThat( response.getBody().getMovieTitle() ).isEqualTo( "The Matrix" );
        }

        @Test
        @DisplayName( "Movie cant be found | return code 404" )
        void getMovie_whenMovieIdIsInvalid_shouldReturnNotFoundCode() {

            // Arrange - create no movie

            // Act - ID of non-existing movie
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response =
                    restTemplate.getForEntity( "/movies/99999", MovieResponseBasicFullOwnershipDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

    }


    // =========================================================
    // PUT /movies/{movieId} - Update Movie
    // =========================================================

    @Nested
    @DisplayName( "PUT /movies/{movieId} - Update Movie" )
    class UpdateMovieTests {

        @Test
        @DisplayName( "Update movie successful | return code 200" )
        void putMovie_whenMovieExists_shouldReturnOkCode() {

            // Arrange - create user, movie-list and movie
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto createRequest = new MovieCreateBasicDto();
            createRequest.setMovieListId( movieList.getMovieListId() );
            createRequest.setMovieTitle( "Old Title" );
            createRequest.setReleaseYear( 1999 );
            MovieResponseBasicFullOwnershipDto created =
                    restTemplate.postForObject( "/movies", createRequest, MovieResponseBasicFullOwnershipDto.class );

            // create update request
            MovieUpdateDto updateRequest = new MovieUpdateDto();
            updateRequest.setMovieTitle( "New Title" );
            updateRequest.setReleaseYear( 2000 );

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response = restTemplate.exchange(
                    "/movies/" + created.getMovieId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieResponseBasicFullOwnershipDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieTitle() ).isEqualTo( "New Title" );
            assertThat( response.getBody().getReleaseYear() ).isEqualTo( 2000 );
        }

        @Test
        @DisplayName( "Movie to change does not exist | return code 404" )
        void putMovie_whenMovieNotExists_shouldReturnNotFoundCode() {

            // Arrange - create no movie
            MovieUpdateDto updateRequest = new MovieUpdateDto();
            updateRequest.setMovieTitle( "New Title" );

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response = restTemplate.exchange(
                    "/movies/99999",
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieResponseBasicFullOwnershipDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

        @Test
        @DisplayName( "Movie title is already in use | return code 409" )
        void putMovie_whenTitleIsAlreadyInUse_shouldReturnConflictCode() {

            // Arrange - create user, movie-list and two movies
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto firstRequest = new MovieCreateBasicDto();
            firstRequest.setMovieListId( movieList.getMovieListId() );
            firstRequest.setMovieTitle( "First Movie" );
            firstRequest.setReleaseYear( 1999 );
            restTemplate.postForEntity( "/movies", firstRequest, MovieResponseBasicFullOwnershipDto.class );

            MovieCreateBasicDto secondRequest = new MovieCreateBasicDto();
            secondRequest.setMovieListId( movieList.getMovieListId() );
            secondRequest.setMovieTitle( "Second Movie" );
            secondRequest.setReleaseYear( 2000 );
            MovieResponseBasicFullOwnershipDto created =
                    restTemplate.postForObject( "/movies", secondRequest, MovieResponseBasicFullOwnershipDto.class );

            // Act - try to update second movie to first movie's name
            MovieUpdateDto updateRequest = new MovieUpdateDto();
            updateRequest.setMovieTitle( "First Movie" );

            ResponseEntity< MovieResponseBasicFullOwnershipDto > response = restTemplate.exchange(
                    "/movies/" + created.getMovieId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieResponseBasicFullOwnershipDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CONFLICT );
        }

    }


    // =========================================================
    // DELETE /movies/{movieId} - Delete Movie
    // =========================================================

    @Nested
    @DisplayName( "DELETE /movies/{movieId} - Delete Movie" )
    class DeleteMovieTests {

        @Test
        @DisplayName( "Delete movie successful | return code 200" )
        void deleteMovie_whenMovieExists_shouldReturnOkCode() {

            // Arrange - create user, movie-list and movie
            MovieListMovieOneLineResponseDto movieList = createUserAndMovieList();

            MovieCreateBasicDto createRequest = new MovieCreateBasicDto();
            createRequest.setMovieListId( movieList.getMovieListId() );
            createRequest.setMovieTitle( "Movie To Delete" );
            createRequest.setReleaseYear( 1999 );
            MovieResponseBasicFullOwnershipDto created =
                    restTemplate.postForObject( "/movies", createRequest, MovieResponseBasicFullOwnershipDto.class );

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response = restTemplate.exchange(
                    "/movies/" + created.getMovieId(),
                    HttpMethod.DELETE,
                    null,
                    MovieResponseBasicFullOwnershipDto.class
            );

            // Assert - deleting was successful
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );

            // check movie is deleted
            ResponseEntity< MovieResponseBasicFullOwnershipDto > getResponse =
                    restTemplate.getForEntity( "/movies/" + created.getMovieId(),
                            MovieResponseBasicFullOwnershipDto.class );
            assertThat( getResponse.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

        @Test
        @DisplayName( "Movie to delete cant be found | return code 404" )
        void deleteMovie_whenMovieNotExists_shouldReturnNotFoundCode() {

            // Act
            ResponseEntity< MovieResponseBasicFullOwnershipDto > response = restTemplate.exchange(
                    "/movies/99999",
                    HttpMethod.DELETE,
                    null,
                    MovieResponseBasicFullOwnershipDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

    }

}