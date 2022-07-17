package pl.mbrzozowski.vulcanizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRequestToUser;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.enums.UserStatusAccount;
import pl.mbrzozowski.vulcanizer.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Long id) {
        UserResponse userResponse = userService.findById(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponse> findByEmail(@Param("email") String email) {
        UserResponse userResponse = userService.findByEmail(email);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserRequest userRequest) {
        User user = new UserRequestToUser().apply(userRequest);
        user.setStatusAccount(UserStatusAccount.NOT_ACTIVATED);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.update(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


}
