package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.service.UserService;
import de.adrianwalter.movie_list_rest_api.payload.CreateUserDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
    Endpoints tested with postman,
    works as intended,

    post-endpoint needs json body:
    {
        "id": number,
        "userName": "test_user name"
    }
 */


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public ResponseEntity<Page<User>> index(Pageable pageable){

        return ResponseEntity.ok(userService.findAll(pageable));
    }


    @PostMapping("")
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserDTO request) {

        return ResponseEntity.ok(userService.create(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {

        return ResponseEntity.ok(userService.findById( id ));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.deleteById(id);

        return ResponseEntity.ok().build();
    }



}
