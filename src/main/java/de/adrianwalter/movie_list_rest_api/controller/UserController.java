package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/users" )
@Tag( name = "Users", description = "Manage Users" )
public class UserController {

    private final UserService userService;


    public UserController( UserService userService ) {

        this.userService = userService;
    }


    @Operation(
            summary = "Update a User",
            description = "Change the name of a single user. Name must be unique."
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully changed username",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema( implementation = UserResponseShortDto.class )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - No User with given ID",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Username is already taken",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )


    @PutMapping( "/{userId}" )
    public ResponseEntity< UserResponseShortDto > updateUser(
            @Parameter( description = "ID of user to update", required = true )
            @PathVariable Long userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New username to update an existing User",
                    required = true,
                    content = @Content( schema = @Schema( implementation = UserUpdateDto.class ) )
            ) @RequestBody @Valid UserUpdateDto userUpdateDto ) {

        UserResponseShortDto responseDto = userService.update( userId, userUpdateDto );

        return ResponseEntity.ok( responseDto );
    }


    @Operation(
            summary = "Get all Users",
            description = "Get a pageable list of all users with basic information"
    )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved a list of all users",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema( implementation = UserResponseShortDto.class ) )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )
    @GetMapping( "" )
    public ResponseEntity< List< UserResponseShortDto > > getAllUsers(
            @Parameter(
                    description = "Pagination parameters (page, size, sort)",
                    example = "page=0&size=10&sort=username,asc"
            )
            Pageable pageable
    ) {

        List< UserResponseShortDto > users = userService.findAll( pageable );

        return ResponseEntity.ok( users );
    }


    //    @Operation(
//            summary = "Get all Users",
//            description = "Get a pageable list of all users with detailed information"
//    )
//    @ApiResponses( value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "Successfully retrieved a detailed list of all users",
//                    content = @Content(
//                            mediaType = "application/json",
//                            array = @ArraySchema( schema = @Schema( implementation = UserResponseShortDto.class ) )
//                    )
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "Internal server error"
//            )
//    } )
// @GetMapping("/details")
    public ResponseEntity< List< UserResponseShortDto > > getAllUsersDetails(
            @Parameter(
                    description = "Pagination parameters (page, size, sort)",
                    example = "page=0&size=10&sort=username,asc"
            )
            Pageable pageable
    ) {

        // ToDo: Implement when Movie full implemented: All Users with all their MovieLists, UserResponseDto

        List< UserResponseShortDto > users = userService.findAll( pageable );

        return ResponseEntity.ok( users );
    }


    @Operation( summary = "Create a new user" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User created successfully",
                    content = @Content( schema = @Schema( implementation = UserResponseShortDto.class ) )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - Username is already taken",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )
    @PostMapping( "" )
    public ResponseEntity< UserResponseShortDto > createNewUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "New username to create a new user",
                    required = true,
                    content = @Content( schema = @Schema( implementation = UserCreateDto.class ) )
            )
            @Valid @RequestBody UserCreateDto requestBody ) {

        UserResponseShortDto responseDto = userService.create( requestBody );

        return ResponseEntity.ok( responseDto );
    }


    @Operation( summary = "Get a user by ID" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User retrieved successfully",
                    content = @Content( schema = @Schema( implementation = UserResponseShortDto.class ) )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - No User with given ID",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )
// ToDo: in test; ok
    @GetMapping( "/{userId}" )
    public ResponseEntity< UserResponseShortDto > getUser(
            @Parameter( description = "ID of user to retrieve", required = true )
            @PathVariable Long userId
    ) {

        UserResponseShortDto responseDto = userService.findUserDetails( userId );

        return ResponseEntity.ok( responseDto );
    }


    //    @Operation( summary = "Get a user and associated movie lists by ID" )
//    @ApiResponses( value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "User retrieved successfully",
//                    content = @Content( schema = @Schema( implementation = UserResponseShortDto.class ) )
//            ),
//            @ApiResponse(
//                    responseCode = "404",
//                    description = "Not Found - No User with given ID"
//            ),
//            @ApiResponse(
//                    responseCode = "500",
//                    description = "Internal server error"
//            )
//    } )
// @GetMapping("/{userId}/details")
    public ResponseEntity< UserResponseShortDto > getUserDetails(
            @Parameter( description = "ID of user to retrieve", required = true )
            @PathVariable Long userId
    ) {

        // ToDo: Implement when Movie full implemented: User with all MovieLists, UserResponseDto

        UserResponseShortDto responseDto = userService.findUserDetails( userId );

        return ResponseEntity.ok( responseDto );
    }


    @Operation( summary = "Delete a user by ID" )
    @ApiResponses( value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User deleted successfully",
                    content = @Content( schema = @Schema( implementation = UserResponseShortDto.class ) )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found - No User with given ID",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    } )
// ToDo: in test; ok
    @DeleteMapping( "/{userId}" )
    public ResponseEntity< UserResponseShortDto > deleteUser(
            @Parameter( description = "ID of user to delete", required = true )
            @PathVariable Long userId
    ) {

        UserResponseShortDto responseDto = userService.deleteById( userId );

        return ResponseEntity.ok( responseDto );
    }


}
