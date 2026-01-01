package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.payload.UserShortResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.UserUpdateDto;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import de.adrianwalter.movie_list_rest_api.payload.UserCreateDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }


    // ToDo: in test; ok
    @PutMapping("/{userId}")
    public ResponseEntity< UserShortResponseDto > updateUser(
            @PathVariable Long userId, @RequestBody @Valid UserUpdateDto userUpdateDto ){

        UserShortResponseDto responseDto = userService.update( userId, userUpdateDto );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok/nested
    // ToDo: Response nested with MovieList
    // ToDo: Remove "Pageable"
    @GetMapping("")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable){

        return ResponseEntity.ok(userService.findAll(pageable));
    }


    // ToDo: in test; ok
    // ToDo: Response should be UserShortResponseDTO
    @PostMapping("")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody UserCreateDto requestBody) {

        return ResponseEntity.ok(userService.create(requestBody));
    }


    // ToDo: in test; ok
    @GetMapping("/{userId}")
    public ResponseEntity< UserShortResponseDto > getUser( @PathVariable Long userId) {

        UserShortResponseDto responseDto = userService.findByIdAndMapToShortResponse( userId );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: Create test
    @GetMapping("/{userId}/details")
    public ResponseEntity< UserShortResponseDto > getUserDetails( @PathVariable Long userId) {

        // ToDo: Implement when Movie full implemented
        // UserShortResponseDto responseDto = userService.findByIdAndMapToDetailedResponse( userId );

        UserShortResponseDto responseDto = userService.findByIdAndMapToShortResponse( userId );
        return ResponseEntity.ok( responseDto );
    }


    // ToDo: in test; ok
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserShortResponseDto> deleteUser(@PathVariable Long userId) {

        UserShortResponseDto responseDto = userService.deleteByIdAndMapToShortResponse(userId);

        return ResponseEntity.ok( responseDto );
    }


}
