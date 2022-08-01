package pl.mbrzozowski.vulcanizer.controller;

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
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.domain.UserPrincipal;
import pl.mbrzozowski.vulcanizer.dto.UserLoginBody;
import pl.mbrzozowski.vulcanizer.dto.UserRegisterBody;
import pl.mbrzozowski.vulcanizer.dto.UserResetPasswordBody;
import pl.mbrzozowski.vulcanizer.dto.UserResponse;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.ExceptionHandling;
import pl.mbrzozowski.vulcanizer.service.UserServiceImpl;
import pl.mbrzozowski.vulcanizer.util.JWTTokenProvider;

import java.util.List;

import static pl.mbrzozowski.vulcanizer.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static pl.mbrzozowski.vulcanizer.constant.SecurityConstant.TOKEN_PREFIX;

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
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterBody userRegisterBody) {
        UserResponse userResponse = userService.register(userRegisterBody);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginBody userLoginBody) {
        authenticate(userLoginBody.getEmail(), userLoginBody.getPassword());
        User user = userService.login(userLoginBody);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        UserResponse userResponse = new UserToUserResponse().convert(user);
        return new ResponseEntity<>(userResponse, jwtHeader, HttpStatus.OK);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmMail(@RequestParam("token") String token) {
        userService.confirmMail(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/resetpass")
    public ResponseEntity<?> resetPasswordStart(@RequestBody UserResetPasswordBody userResetPasswordBody) {
        userService.resetPasswordStart(userResetPasswordBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //only for tests - remove or change this method
    @GetMapping()
    public ResponseEntity<List<UserResponse>> findAll(@RequestHeader HttpHeaders headers) {
        List<String> strings = headers.get(headers.AUTHORIZATION);
        String token = strings.stream().findFirst().get();

        String username = null;
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            token = token.substring(TOKEN_PREFIX.length());
            username = jwtTokenProvider.getSubject(token);
        }

        log.info(username);
        List<UserResponse> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
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
