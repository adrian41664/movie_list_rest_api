package de.adrianwalter.movie_list_rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest( controllers = UserController.class )
class UserControllerTest {

    // Web-Layer-Tests only!

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    // -------------------------
    // POST /users
    // -------------------------


    @Test
    void createNewUser_withUserName_returns200AndCorrectBody() throws Exception {

        // Arrange
        UserCreateDto request = new UserCreateDto();
        request.setUserName( "User123" );

        UserResponseShortDto mockResponse = new UserResponseShortDto();
        mockResponse.setUserId( 1L );
        mockResponse.setUserName( "User123" );

        when( userService.create( any( UserCreateDto.class ) ) ).thenReturn( mockResponse );

        // Act & Assert
        mockMvc.perform( post( "/users" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( objectMapper.writeValueAsString( request ) ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.userId" ).value( 1 ) )
                .andExpect( jsonPath( "$.userName" ).value( "User123" ) );
    }


    @Test
    void createNewUser_withAlias_returns200AndCorrectBody() throws Exception {
        // Arrange
        // testing @JsonAlias
        String requestBody = """
                { "userName": "User123" }
                """;

        UserResponseShortDto mockResponse = new UserResponseShortDto();
        mockResponse.setUserId( 1L );
        mockResponse.setUserName( "User123" );

        when( userService.create( any( UserCreateDto.class ) ) ).thenReturn( mockResponse );

        // Act & Assert
        mockMvc.perform( post( "/users" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( requestBody ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath( "$.userName" ).value( "User123" ) );
    }


    @Test
    void createNewUser_withBlankUserName_returns400() throws Exception {
        // Arrange
        // testing @NotBlank validation
        String requestBody = """
                { "userName": "" }
                """;

        // Act & Assert
        mockMvc.perform( post( "/users" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( requestBody ) )
                .andExpect( status().isBadRequest() );
    }


    @Test
    void createNewUser_withMissingUserName_returns400() throws Exception {
        // Arrange
        String requestBody = "{}";

        // Act & Assert
        mockMvc.perform( post( "/users" )
                        .contentType( MediaType.APPLICATION_JSON )
                        .content( requestBody ) )
                .andExpect( status().isBadRequest() );
    }
}