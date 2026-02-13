package de.adrianwalter.movie_list_rest_api.controller;

import static org.junit.jupiter.api.Assertions.*;

import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateByUserIdBodyDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListCreateByUserNameBodyDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListMovieOneLineResponseDto;
import de.adrianwalter.movie_list_rest_api.dto.movielist.MovieListUpdateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.repository.MovieListRepository;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@AutoConfigureTestDatabase( replace = AutoConfigureTestDatabase.Replace.ANY )
@ActiveProfiles( "test" )
@DisplayName( "MovieListController Integration Tests" )
class MovieListControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieListRepository movieListRepository;


    @BeforeEach
    void cleanUp() {
        // clean up, before every test
        movieListRepository.deleteAll();
        userRepository.deleteAll();
    }


    // =========================================================
    // POST /movie-lists - Create a new MovieList
    // =========================================================

    @Nested
    @DisplayName( "POST /movie-lists - Create MovieList" )
    class CreateMovieListTests {


        @Test
        @DisplayName( "Create a new movie-list by user ID | return 200" )
        void createMovieList_whenValidByUserId_shouldReturnSuccessCode() {

            // Arrange - create user first
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto request = new MovieListCreateByUserIdBodyDto();
            request.setMovieListName( "My Movie List" );
            request.setUserId( user.getUserId() );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.postForEntity( "/movie-lists", request, MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieListId() ).isNotNull();
            assertThat( response.getBody().getMovieListName() ).isEqualTo( "My Movie List" );
        }


        @Test
        @DisplayName( "Create a new movie-list by user name | return 200" )
        void createMovieList_whenValidByUserName_shouldReturnSuccessCode() {

            // Arrange - create user first
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            restTemplate.postForEntity( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserNameBodyDto request = new MovieListCreateByUserNameBodyDto();
            request.setMovieListName( "My Movie List" );
            request.setUserName( "User123" );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.postForEntity( "/movie-lists", request, MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieListId() ).isNotNull();
            assertThat( response.getBody().getMovieListName() ).isEqualTo( "My Movie List" );
        }


        @Test
        @DisplayName( "User not found | return code 404" )
        void createMovieList_whenUserNotFound_shouldReturnNotFoundCode() {

            // Arrange - create no user
            MovieListCreateByUserIdBodyDto request = new MovieListCreateByUserIdBodyDto();
            request.setMovieListName( "My Movie List" );
            request.setUserId( 99999L );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.postForEntity( "/movie-lists", request, MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }


        @Test
        @DisplayName( "Movie-list name is already in use for user | return code 409" )
        void createMovieList_whenNameAlreadyInUseForUser_shouldReturnConflictCode() {

            // Arrange - create user and first movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto firstRequest = new MovieListCreateByUserIdBodyDto();
            firstRequest.setMovieListName( "My Movie List" );
            firstRequest.setUserId( user.getUserId() );
            restTemplate.postForEntity( "/movie-lists", firstRequest, MovieListMovieOneLineResponseDto.class );

            // Act - try to create second movie-list with same name
            MovieListCreateByUserIdBodyDto duplicateRequest = new MovieListCreateByUserIdBodyDto();
            duplicateRequest.setMovieListName( "My Movie List" );
            duplicateRequest.setUserId( user.getUserId() );

            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.postForEntity( "/movie-lists", duplicateRequest, MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CONFLICT );
        }


        @Test
        @DisplayName( "Movie-list name is blank | return code 400" )
        void createMovieList_whenNameIsBlank_shouldReturnBadRequestCode() {

            // Arrange - create user first
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto request = new MovieListCreateByUserIdBodyDto();
            request.setMovieListName( "" );
            request.setUserId( user.getUserId() );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.postForEntity( "/movie-lists", request, MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
        }

    }


    // =========================================================
    // GET /movie-lists/{movieListId} - Retrieve single movie-list
    // =========================================================

    @Nested
    @DisplayName( "GET /movie-lists/{movieListId} - Get MovieList by ID" )
    class GetMovieListTests {

        @Test
        @DisplayName( "Return movie-list successful | return code 200" )
        void getMovieList_whenMovieListIdIsValid_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "My Movie List" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/" + created.getMovieListId(),
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieListId() ).isEqualTo( created.getMovieListId() );
            assertThat( response.getBody().getMovieListName() ).isEqualTo( "My Movie List" );
        }


        @Test
        @DisplayName( "Movie-list cant be found | return code 404" )
        void getMovieList_whenMovieListIdIsInvalid_shouldReturnNotFoundCode() {

            // Arrange - create no movie-list

            // Act - ID of non-existing movie-list
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/99999", MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

    }


    // =========================================================
    // GET /movie-lists/user/{userName} - Retrieve all movie-lists of user
    // =========================================================

    @Nested
    @DisplayName( "GET /movie-lists/user/{userName} - Get all MovieLists of User" )
    class GetAllMovieListsOfUserTests {

        @Test
        @DisplayName( "Return empty list successful | return code 200" )
        void getAllMovieListsOfUser_whenNoMovieListExists_shouldReturnEmptyListAndOkCode() {

            // Arrange - create user without movie-lists
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            restTemplate.postForEntity( "/users", userRequest, UserResponseShortDto.class );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto[] > response =
                    restTemplate.getForEntity( "/movie-lists/user/User123", MovieListMovieOneLineResponseDto[].class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isEmpty();
        }


        @Test
        @DisplayName( "Return list of all movie-lists of user successful | return code 200" )
        void getAllMovieListsOfUser_whenMovieListsExist_shouldReturnListAndOkCode() {

            // Arrange - create user and two movie-lists
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto firstRequest = new MovieListCreateByUserIdBodyDto();
            firstRequest.setMovieListName( "First List" );
            firstRequest.setUserId( user.getUserId() );
            restTemplate.postForEntity( "/movie-lists", firstRequest, MovieListMovieOneLineResponseDto.class );

            MovieListCreateByUserIdBodyDto secondRequest = new MovieListCreateByUserIdBodyDto();
            secondRequest.setMovieListName( "Second List" );
            secondRequest.setUserId( user.getUserId() );
            restTemplate.postForEntity( "/movie-lists", secondRequest, MovieListMovieOneLineResponseDto.class );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto[] > response =
                    restTemplate.getForEntity( "/movie-lists/user/User123", MovieListMovieOneLineResponseDto[].class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).hasSize( 2 );
        }


        @Test
        @DisplayName( "User not found | return code 404" )
        void getAllMovieListsOfUser_whenUserNotFound_shouldReturnNotFoundCode() {

            // Arrange - create no user

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/user/NonExistentUser",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

    }


    // =========================================================
    // GET /movie-lists/{movieListName}/user/{userName} - Get MovieList by name and username
    // =========================================================

    @Nested
    @DisplayName( "GET /movie-lists/{movieListName}/user/{userName} - Get MovieList by name and username" )
    class GetMovieListByNameAndUserNameTests {

        @Test
        @DisplayName( "Return movie-list successful | return code 200" )
        void getMovieListByNameAndUserName_whenValid_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "My Movie List" );
            createRequest.setUserId( user.getUserId() );
            restTemplate.postForEntity( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/My Movie List/user/User123",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieListName() ).isEqualTo( "My Movie List" );
        }


        @Test
        @DisplayName( "Movie-list not found | return code 404" )
        void getMovieListByNameAndUserName_whenMovieListNotFound_shouldReturnNotFoundCode() {

            // Arrange - create user without movie-lists
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            restTemplate.postForEntity( "/users", userRequest, UserResponseShortDto.class );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/NonExistentList/user/User123",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }


        @Test
        @DisplayName( "User not found | return code 404" )
        void getMovieListByNameAndUserName_whenUserNotFound_shouldReturnNotFoundCode() {

            // Arrange - create no user

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/My Movie List/user/NonExistentUser",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

    }


    // =========================================================
    // PUT /movie-lists/{movieListId} - Update MovieList
    // =========================================================

    @Nested
    @DisplayName( "PUT /movie-lists/{movieListId} - Update MovieList" )
    class UpdateMovieListTests {

        @Test
        @DisplayName( "Update movie-list successful with name and description | return code 200" )
        void putMovieList_whenMovieListExists_whenNewNameAndDescription_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "Old Name" );
            createRequest.setDescription( "Old Description" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // create update request
            MovieListUpdateDto updateRequest = new MovieListUpdateDto();
            updateRequest.setMovieListName( "New Name" );
            updateRequest.setMovieListDescription( "New Description" );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/" + created.getMovieListId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieListName() ).isEqualTo( "New Name" );
            assertThat( response.getBody().getDescription() ).isEqualTo( "New Description" );
        }
        @Test
        @DisplayName( "Update movie-list successful with name | return code 200" )
        void putMovieList_whenMovieListExists_whenNewName_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "Old Name" );
            createRequest.setDescription( "Old Description" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // create update request
            MovieListUpdateDto updateRequest = new MovieListUpdateDto();
            updateRequest.setMovieListName( "New Name" );
            updateRequest.setMovieListDescription( "" );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/" + created.getMovieListId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieListName() ).isEqualTo( "New Name" );
            assertThat( response.getBody().getDescription() ).isEqualTo( "Old Description" );
        }

        @Test
        @DisplayName( "Update movie-list successful with description | return code 200" )
        void putMovieList_whenMovieListExists_whenNewDescription_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "Old Name" );
            createRequest.setDescription( "Old Description" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // create update request
            MovieListUpdateDto updateRequest = new MovieListUpdateDto();
            updateRequest.setMovieListName( "" );
            updateRequest.setMovieListDescription( "New Description" );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/" + created.getMovieListId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getMovieListName() ).isEqualTo( "Old Name" );
            assertThat( response.getBody().getDescription() ).isEqualTo( "New Description" );
        }

        @Test
        @DisplayName( "No update of movie-list when no name and description | return code 422" )
        void putMovieList_whenMovieListExists_whenNoNameAndDescription_shouldReturnBadRequestCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "Old Name" );
            createRequest.setDescription( "Old Description" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // create update request
            MovieListUpdateDto updateRequest = new MovieListUpdateDto();
            updateRequest.setMovieListName( "" );
            updateRequest.setMovieListDescription( "" );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/" + created.getMovieListId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
//            Assertions.assertNotNull( response.getBody() );
//            assertThat( response.getBody().getMovieListName() ).isEqualTo( "Old Name" );
//            assertThat( response.getBody().getDescription() ).isEqualTo( "Old Description" );
        }


        @Test
        @DisplayName( "Movie-list to change does not exist | return code 404" )
        void putMovieList_whenMovieListNotExists_shouldReturnNotFoundCode() {

            // Arrange - create no movie-list
            MovieListUpdateDto updateRequest = new MovieListUpdateDto();
            updateRequest.setMovieListName( "New Name" );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/99999",
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }


        @Test
        @DisplayName( "Movie-list name is already in use | return code 409" )
        void putMovieList_whenNameIsAlreadyInUse_shouldReturnConflictCode() {

            // Arrange - create user and two movie-lists
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto firstRequest = new MovieListCreateByUserIdBodyDto();
            firstRequest.setMovieListName( "First List" );
            firstRequest.setUserId( user.getUserId() );
            restTemplate.postForEntity( "/movie-lists", firstRequest, MovieListMovieOneLineResponseDto.class );

            MovieListCreateByUserIdBodyDto secondRequest = new MovieListCreateByUserIdBodyDto();
            secondRequest.setMovieListName( "Second List" );
            secondRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", secondRequest, MovieListMovieOneLineResponseDto.class );

            // Act - try to update second list to first list's name
            MovieListUpdateDto updateRequest = new MovieListUpdateDto();
            updateRequest.setMovieListName( "First List" );

            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/" + created.getMovieListId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CONFLICT );
        }

    }


    // =========================================================
    // DELETE /movie-lists/{movieListId} - Delete MovieList
    // =========================================================

    @Nested
    @DisplayName( "DELETE /movie-lists/{movieListId} - Delete MovieList" )
    class DeleteMovieListTests {

        @Test
        @DisplayName( "Delete movie-list successful | return code 200" )
        void deleteMovieList_whenMovieListExists_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "List To Delete" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/" + created.getMovieListId(),
                    HttpMethod.DELETE,
                    null,
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert - deleting was successful
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );

            // check movie-list is deleted
            ResponseEntity< MovieListMovieOneLineResponseDto > getResponse =
                    restTemplate.getForEntity( "/movie-lists/" + created.getMovieListId(),
                            MovieListMovieOneLineResponseDto.class );
            assertThat( getResponse.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }


        @Test
        @DisplayName( "Movie-list to delete cant be found | return code 404" )
        void deleteMovieList_whenMovieListNotExists_shouldReturnNotFoundCode() {

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response = restTemplate.exchange(
                    "/movie-lists/99999",
                    HttpMethod.DELETE,
                    null,
                    MovieListMovieOneLineResponseDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

    }


    // =========================================================
    // GET /movie-lists/{movieListId}/search - Search and filter movies
    // =========================================================

    @Nested
    @DisplayName( "GET /movie-lists/{movieListId}/search - Search and filter movies" )
    class SearchMovieListTests {

        @Test
        @DisplayName( "Return movie-list with search results | return code 200" )
        void searchMovieList_whenMovieListExists_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "My Movie List" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // Act - search with default parameters
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/" + created.getMovieListId() + "/search",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
        }


        @Test
        @DisplayName( "Movie-list not found | return code 404" )
        void searchMovieList_whenMovieListNotFound_shouldReturnNotFoundCode() {

            // Act
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity( "/movie-lists/99999/search",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }

        @Test
        @DisplayName( "Search with query parameters; sort with invalid parameter | return code 200" )
        void searchMovieList_whenSortParameterInvalid_shouldReturnOkCode() {

            // Returns ok code, even if sort parameter is invalid and therefore not applied

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "My Movie List" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // Act - search with query parameters
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity(
                            "/movie-lists/" + created.getMovieListId() +
                                    "/search?title=Matrix&minRating=3&sortBy=___________&ascending=false",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
        }


        @Test
        @DisplayName( "Search with query parameters | return code 200" )
        void searchMovieList_whenQueryParametersProvided_shouldReturnOkCode() {

            // Arrange - create user and movie-list
            UserCreateDto userRequest = new UserCreateDto();
            userRequest.setUserName( "User123" );
            UserResponseShortDto user =
                    restTemplate.postForObject( "/users", userRequest, UserResponseShortDto.class );

            MovieListCreateByUserIdBodyDto createRequest = new MovieListCreateByUserIdBodyDto();
            createRequest.setMovieListName( "My Movie List" );
            createRequest.setUserId( user.getUserId() );
            MovieListMovieOneLineResponseDto created =
                    restTemplate.postForObject( "/movie-lists", createRequest, MovieListMovieOneLineResponseDto.class );

            // Act - search with query parameters
            ResponseEntity< MovieListMovieOneLineResponseDto > response =
                    restTemplate.getForEntity(
                            "/movie-lists/" + created.getMovieListId() + "/search?title=Matrix&minRating=3&sortBy=rating&ascending=false",
                            MovieListMovieOneLineResponseDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
        }

    }

}