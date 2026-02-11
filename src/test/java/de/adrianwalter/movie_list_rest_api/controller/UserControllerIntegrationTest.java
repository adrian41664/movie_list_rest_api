package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
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

    // docker must be started up before start of tests
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
    void setUp() {
        // clean up, before every test
        userRepository.deleteAll();
    }


    @Nested
    @DisplayName( "POST /users - Create User" )
    class CreateUserTests {

        @Test
        @DisplayName( "Should create a new user" )
        void shouldCreateNewUser() {
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

    }



}