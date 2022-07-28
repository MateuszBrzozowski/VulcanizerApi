package pl.mbrzozowski.vulcanizer.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.dto.FavoritesRequest;
import pl.mbrzozowski.vulcanizer.dto.UserRegisterBody;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRegisterBodyToUserRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.service.BusinessService;
import pl.mbrzozowski.vulcanizer.service.UserServiceImpl;

import java.util.List;

@Controller
@RestController
@Data
@Slf4j
//@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userService;
    private final BusinessService businessService;
    protected final Logger logger = LoggerFactory.getLogger(UserController.class);

    // hasRole('ROLE_') hasAnyRole('ROLE_') hastAuthority('permission') hasAnyAuthority('permission')

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
//        userService.save(new UserRegisterBodyToUserRequest().apply(userRequest));
        log.info("Post Mapping good");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.update(userRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/users/favorites")
    public ResponseEntity<Boolean> saveFavorite(@RequestBody FavoritesRequest favoritesRequest) {
        boolean isAdd = userService.saveFavorite(favoritesRequest);
        return new ResponseEntity<>(isAdd, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRegisterBody userRequest) {
//        UserResponse response = userService.login(userRequest);
        log.info("Jest dobrze, dobrze jest");
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
