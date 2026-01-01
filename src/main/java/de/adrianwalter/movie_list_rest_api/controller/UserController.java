package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import de.adrianwalter.movie_list_rest_api.payload.PostUserDTO;
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


    // ToDo: in test; ok/nested
    // ToDo: Response nested with MovieList
    // ToDo: Remove "Pageable"
    @GetMapping("")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable){

        return ResponseEntity.ok(userService.findAll(pageable));
    }


    // ToDo: in test; ok/nested
    // ToDo: Response nested with MovieList
    @PostMapping("")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody PostUserDTO requestBody) {

        return ResponseEntity.ok(userService.create(requestBody));
    }


    // ToDo: in test; ok/nested
    // ToDo: Response nested with MovieList
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {

        return ResponseEntity.ok( userService.findById( id ));
    }


    // ToDo: in test; ok
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteById(id);

        return ResponseEntity.ok().build();
    }


}
