package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@Testcontainers
@DisplayName( "UserController Integration Tests" )
//@TestPropertySource(properties = {
//        "logging.level.org.testcontainers=DEBUG",
//        "testcontainers.verbose=true"
//})
class UserControllerIntegrationTest {

    // docker must be started before start of tests
    @ServiceConnection
    @Container
    static MySQLContainer< ? > mysql = new MySQLContainer<>( "mysql:8.0" );

    @DynamicPropertySource
    static void properties( DynamicPropertyRegistry registry ) {
        registry.add( "spring.datasource.url", mysql::getJdbcUrl );
        registry.add( "spring.datasource.username", mysql::getUsername );
        registry.add( "spring.datasource.password", mysql::getPassword );
    }


    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void cleanUp() {
        // clean up, before every test
        userRepository.deleteAll();
    }


    // =========================================================
    // POST /users - Create a new User
    // =========================================================


    @Nested
    @DisplayName( "POST /users - Create User" )
    class CreateUserTests {

        @Test
        @DisplayName( "Should create a new user | return 200" )
        void createUser_whenValidUserNameCharsAndNumbers_shouldReturnSuccessCode() {
            
            // Arrange
            UserCreateDto request = new UserCreateDto();
            request.setUserName( "User123" );

            // Act
            ResponseEntity< UserResponseShortDto > response =
                    restTemplate.postForEntity( "/users", request, UserResponseShortDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getUserId() ).isNotNull();
            assertThat( response.getBody().getUserName() ).isEqualTo( "User123" );
        }

        @Test
        @DisplayName( "Should create a new user with special signs as username  | return 200" )
        void createUser_whenValidUserNameSpecialSigns_shouldReturnSuccessCode() {

            // Arrange
            UserCreateDto request = new UserCreateDto();
            request.setUserName( "User123" );

            // Act
            ResponseEntity< UserResponseShortDto > response =
                    restTemplate.postForEntity( "/users", request, UserResponseShortDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isNotNull();
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getUserId() ).isNotNull();
            assertThat( response.getBody().getUserName() ).isEqualTo( "User123" );
        }

        @Test
        @DisplayName( "Username is already in use | return code 409" )
        void createUser_whenUserNameInUse_shouldReturnConflictCode(){

            // Arrange - create first user
            UserCreateDto firstUser = new UserCreateDto();
            firstUser.setUserName( "User123" );
            restTemplate.postForEntity( "/users", firstUser, UserResponseShortDto.class );

            // Act - try username already in use for second user
            UserCreateDto duplicateUser = new UserCreateDto();
            duplicateUser.setUserName( "User123" );
            ResponseEntity<UserResponseShortDto> response =
                    restTemplate.postForEntity( "/users", duplicateUser, UserResponseShortDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CONFLICT );
        }

        @Test
        @DisplayName( "Username is blank | return code 400" )
        void createUser_whenUserNameIsBlank_shouldReturnBadRequestCode(){

            // Arrange
            UserCreateDto request = new UserCreateDto();

            // create empty username - @Valid should prevent this
            request.setUserName( "" );

            // Act
            ResponseEntity<UserResponseShortDto> response =
                    restTemplate.postForEntity( "/users", request, UserResponseShortDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.BAD_REQUEST );
        }

    }


    // =========================================================
    // GET /users/{userId} - Retrieve single user
    // =========================================================

    @Nested
    @DisplayName( "GET /users/{userId} - Get User by ID" )
    class GetUserTests {


        @Test
        @DisplayName( "Return user successful | return code 200" )
        void getUser_whenUserIdIsValid_shouldReturnOkCode() {

            // Arrange - create user
            UserCreateDto createRequest = new UserCreateDto();
            createRequest.setUserName( "User123" );
            UserResponseShortDto created =
                    restTemplate.postForObject( "/users", createRequest, UserResponseShortDto.class );

            // Act
            ResponseEntity< UserResponseShortDto > response =
                    restTemplate.getForEntity( "/users/" + created.getUserId(), UserResponseShortDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getUserId() ).isEqualTo( created.getUserId() );
            assertThat( response.getBody().getUserName() ).isEqualTo( "User123" );
        }


        // user cant be found / 404

        @Test
        @DisplayName( "User cant be found | return code 404" )
        void getUser_whenUserIdIsInvalid_shouldReturnNotFoundCode() {

            // Arrange - create no user

            // Act - ID of non-existing user
            ResponseEntity<UserResponseShortDto> response =
                    restTemplate.getForEntity( "/users/2", UserResponseShortDto.class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }


    }

    // =========================================================
    // GET /users - Retrieve all users
    // =========================================================

    @Nested
    @DisplayName( "GET /users - Retrieve all users" )
    class GetAllUsersTests {

        @Test
        @DisplayName( "Return empty list successful | return code 200" )
        void getAllUser_whenNoUserIsExisting_shouldReturnEmptyListAndOkCode() {

            // Arrange - create no users

            // Act
            ResponseEntity<UserResponseShortDto[]> response =
                    restTemplate.getForEntity( "/users", UserResponseShortDto[].class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).isEmpty();
        }

        @Test
        @DisplayName( "Return list of all users successful | return code 200" )
        void getAllUser_whenUsersExist_shouldReturnListAndOkCode() {

            // Arrange - create two users
            UserCreateDto firstUser = new UserCreateDto();
            firstUser.setUserName( "User123" );
            restTemplate.postForEntity( "/users", firstUser, UserResponseShortDto.class );

            UserCreateDto secondUser = new UserCreateDto();
            secondUser.setUserName( "User456" );
            restTemplate.postForEntity( "/users", secondUser, UserResponseShortDto.class );

            // Act
            ResponseEntity<UserResponseShortDto[]> response =
                    restTemplate.getForEntity( "/users", UserResponseShortDto[].class );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            assertThat( response.getBody() ).hasSize( 2 );
        }
    }


    // =========================================================
    // PUT /users/{userID} - Patch|Update User
    // =========================================================

    @Nested
    @DisplayName( "PUT /users/{userID} - Patch|Update User" )
    class UpdateUserTests {


        @Test
        @DisplayName( "Update user successful | return code 200" )
        void putUser_whenUserExist_shouldReturnOkCode() {

            // Arrange - create user
            UserCreateDto createRequest = new UserCreateDto();
            createRequest.setUserName( "User123" );
            UserResponseShortDto created =
                    restTemplate.postForObject( "/users", createRequest, UserResponseShortDto.class );

            // create user update name
            UserUpdateDto updateRequest = new UserUpdateDto();
            updateRequest.setUserName( "User456" );

            // Act
            ResponseEntity<UserResponseShortDto> response = restTemplate.exchange(
                    "/users/" + created.getUserId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    UserResponseShortDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.OK );
            Assertions.assertNotNull( response.getBody() );
            assertThat( response.getBody().getUserName() ).isEqualTo( "User456" );
        }


        // User to change does not exist / 404
        @Test
        @DisplayName( "User to change does not exist | return code 404" )
        void putUser_whenUserNotExist_shouldReturnNotFoundCode() {

            // Arrange -  create no user

            // create update body
            UserUpdateDto updateRequest = new UserUpdateDto();
            updateRequest.setUserName( "User456" );


            // Act
            ResponseEntity<UserResponseShortDto> response = restTemplate.exchange(
                    "/users/99999",
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    UserResponseShortDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.NOT_FOUND );
        }


        @Test
        @DisplayName( "Username is already in use | return code 409" )
        void putUser_whenUserNameIsAlreadyInUse_shouldReturnConflictCode() {

            // Arrange - Create two users
            UserCreateDto firstUser = new UserCreateDto();
            firstUser.setUserName( "User123" );
            restTemplate.postForEntity( "/users", firstUser, UserResponseShortDto.class );

            UserCreateDto secondUser = new UserCreateDto();
            secondUser.setUserName( "User456" );
            UserResponseShortDto created =
                    restTemplate.postForObject( "/users", secondUser, UserResponseShortDto.class );

            // Act - try update user with name already in use
            UserUpdateDto updateRequest = new UserUpdateDto();
            updateRequest.setUserName( "User123" );

            ResponseEntity<UserResponseShortDto> response = restTemplate.exchange(
                    "/users/" + created.getUserId(),
                    HttpMethod.PUT,
                    new HttpEntity<>( updateRequest ),
                    UserResponseShortDto.class
            );

            // Assert
            assertThat( response.getStatusCode() ).isEqualTo( HttpStatus.CONFLICT );
        }


    }


    // =========================================================
    // DELETE /users/{userID} - Delete User
    // =========================================================

    @Nested
    @DisplayName( "DELETE /users/{userID} - Delete User" )
    class DeleteUserTests {

        // Delete successful

        // Delete returns 404 / User not found

    }



}