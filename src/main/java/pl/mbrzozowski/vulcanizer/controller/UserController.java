package pl.mbrzozowski.vulcanizer.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mbrzozowski.vulcanizer.domain.UserPrincipal;
import pl.mbrzozowski.vulcanizer.dto.UserLoginBody;
import pl.mbrzozowski.vulcanizer.dto.UserRegisterBody;
import pl.mbrzozowski.vulcanizer.dto.UserRequest;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserRegisterBodyToUserRequest;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.ExceptionHandling;
import pl.mbrzozowski.vulcanizer.service.UserServiceImpl;
import pl.mbrzozowski.vulcanizer.util.JWTTokenProvider;

import static pl.mbrzozowski.vulcanizer.constant.SecurityConstant.JWT_TOKEN_HEADER;

@Controller
@RestController
@Slf4j
@RequestMapping(path = {"/", "/users"})
public class UserController extends ExceptionHandling {
    private final UserServiceImpl userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    protected final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserServiceImpl userService,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegisterBody userRegisterBody) {
        UserRequest userRequest = new UserRegisterBodyToUserRequest().convert(userRegisterBody);
        if (userRequest != null) {
            User user = userService.save(userRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserLoginBody userLoginBody) {
        authenticate(userLoginBody.getEmail(), userLoginBody.getPassword());
        User user = new User();
        if (userService.findByEmail(userLoginBody.getEmail()).isPresent()) {
            user = userService.findByEmail(userLoginBody.getEmail()).get();
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(user, jwtHeader, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }


//    @GetMapping
////    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    public ResponseEntity<List<UserResponse>> findAll() {
//        List<UserResponse> users = userService.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponse> findById(@PathVariable("id") Long id) {
//        UserResponse userResponse = new UserToUserResponse().apply(userService.findById(id));
//        return new ResponseEntity<>(userResponse, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<?> save(@RequestBody UserRegisterBody userRequest) {
////        userService.save(new UserRegisterBodyToUserRequest().apply(userRequest));
//        log.info("Post Mapping good");
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @PutMapping
//    public ResponseEntity<?> update(@RequestBody UserRequest userRequest) {
//        UserResponse userResponse = userService.update(userRequest);
//        return new ResponseEntity<>(userResponse, HttpStatus.OK);
//    }
//
//    @PostMapping("/users/favorites")
//    public ResponseEntity<Boolean> saveFavorite(@RequestBody FavoritesRequest favoritesRequest) {
//        boolean isAdd = userService.saveFavorite(favoritesRequest);
//        return new ResponseEntity<>(isAdd, HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<UserResponse> login(@RequestBody UserRegisterBody userRequest) {
////        UserResponse response = userService.login(userRequest);
//        log.info("Jest dobrze, dobrze jest");
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}
