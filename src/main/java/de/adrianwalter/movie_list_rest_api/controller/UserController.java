package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.payload.user.UserShortResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import de.adrianwalter.movie_list_rest_api.payload.user.UserCreateDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/users" )
public class UserController {

    private final UserService userService;

    public UserController( UserService userService ) {

        this.userService = userService;
    }


    // ToDo: in test; ok
    @PutMapping( "/{userId}" )
    public ResponseEntity< UserShortResponseDto > updateUser(
            @PathVariable Long userId, @RequestBody @Valid UserUpdateDto userUpdateDto ) {

        UserShortResponseDto responseDto = userService.update( userId, userUpdateDto );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @GetMapping( "" )
    public ResponseEntity< List< UserShortResponseDto > > getAllUsers( Pageable pageable ) {

        List< UserShortResponseDto > users = userService.findAll( pageable );

        return ResponseEntity.ok( users );
    }


    // ToDo: in test; ok
    @PostMapping( "" )
    public ResponseEntity< UserShortResponseDto > createNewUser( @Valid @RequestBody UserCreateDto requestBody ) {

        UserShortResponseDto responseDto = userService.create( requestBody );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @GetMapping( "/{userId}" )
    public ResponseEntity< UserShortResponseDto > getUser( @PathVariable Long userId ) {

        UserShortResponseDto responseDto = userService.findUserAndMapToShortResponse( userId );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @DeleteMapping( "/{userId}" )
    public ResponseEntity< UserShortResponseDto > deleteUser( @PathVariable Long userId ) {

        UserShortResponseDto responseDto = userService.deleteByIdAndMapToShortResponse( userId );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: Create test
    //@GetMapping("/{userId}/details")
    public ResponseEntity< UserShortResponseDto > getUserDetails( @PathVariable Long userId ) {

        // ToDo: Implement when Movie full implemented
        // UserShortResponseDto responseDto = userService.findByIdAndMapToDetailedResponse( userId );

        UserShortResponseDto responseDto = userService.findUserAndMapToShortResponse( userId );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: create test
    //@GetMapping("/details")
    public ResponseEntity< List< UserShortResponseDto > > getAllUsersDetails( Pageable pageable ) {

        // ToDo: Implement when Movie full implemented
        List< UserShortResponseDto > users = userService.findAll( pageable );

        return ResponseEntity.ok( users );
    }


    // ToDo: in test; ok
    // @GetMapping("")
    public ResponseEntity< Page< UserShortResponseDto > > OLD_getAllUsers( Pageable pageable ) {

        Page< UserShortResponseDto > users = userService.UNUSED_findAll( pageable );

        return ResponseEntity.ok( users );
    }


}
