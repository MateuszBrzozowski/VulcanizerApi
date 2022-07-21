package pl.mbrzozowski.vulcanizer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.UserRegisterBody;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRegisterBodyToUserRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.service.UserService;

import java.util.List;

@Controller
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
        UserResponse userResponse = new UserToUserResponse().apply(userService.findById(id));
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody UserRegisterBody userRequest) {
        userService.save(new UserRegisterBodyToUserRequest().apply(userRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.update(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }


}
