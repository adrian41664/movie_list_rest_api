package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.dto.user.UserResponseShortDto;
import de.adrianwalter.movie_list_rest_api.dto.user.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import de.adrianwalter.movie_list_rest_api.dto.user.UserCreateDto;
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
    public ResponseEntity< UserResponseShortDto > updateUser(
            @PathVariable Long userId, @RequestBody @Valid UserUpdateDto userUpdateDto ) {

        UserResponseShortDto responseDto = userService.update( userId, userUpdateDto );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @GetMapping( "" )
    public ResponseEntity< List< UserResponseShortDto > > getAllUsers( Pageable pageable ) {

        List< UserResponseShortDto > users = userService.findAll( pageable );

        return ResponseEntity.ok( users );
    }


    // ToDo: in test; ok
    @PostMapping( "" )
    public ResponseEntity< UserResponseShortDto > createNewUser( @Valid @RequestBody UserCreateDto requestBody ) {

        UserResponseShortDto responseDto = userService.create( requestBody );

        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @GetMapping( "/{userId}" )
    public ResponseEntity< UserResponseShortDto > getUser( @PathVariable Long userId ) {

        UserResponseShortDto responseDto = userService.findUserDetails( userId );

        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @DeleteMapping( "/{userId}" )
    public ResponseEntity< UserResponseShortDto > deleteUser( @PathVariable Long userId ) {

        UserResponseShortDto responseDto = userService.deleteById( userId );

        return ResponseEntity.ok( responseDto );
    }


    // ToDo: Create test
    // @GetMapping("/{userId}/details")
    public ResponseEntity< UserResponseShortDto > getUserDetails( @PathVariable Long userId ) {

        // ToDo: Implement when Movie full implemented: User with all MovieLists, UserResponseDto

        UserResponseShortDto responseDto = userService.findUserDetails( userId );

        return ResponseEntity.ok( responseDto );
    }


    // ToDo: create test
    // @GetMapping("/details")
    public ResponseEntity< List< UserResponseShortDto > > getAllUsersDetails( Pageable pageable ) {

        // ToDo: Implement when Movie full implemented: All Users with all their MovieLists, UserResponseDto

        List< UserResponseShortDto > users = userService.findAll( pageable );

        return ResponseEntity.ok( users );
    }


}
