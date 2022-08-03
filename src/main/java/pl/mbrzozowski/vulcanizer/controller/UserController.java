package pl.mbrzozowski.vulcanizer.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.mbrzozowski.vulcanizer.domain.UserPrincipal;
import pl.mbrzozowski.vulcanizer.dto.*;
import pl.mbrzozowski.vulcanizer.dto.mapper.UserToUserResponse;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.exceptions.ExceptionHandling;
import pl.mbrzozowski.vulcanizer.service.UserServiceImpl;
import pl.mbrzozowski.vulcanizer.util.JWTTokenProvider;

import java.util.Optional;

import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_ID;
import static pl.mbrzozowski.vulcanizer.constant.AppHttpHeaders.SUM_CONTROL_PROPERTIES;
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
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterBody userRegisterBody) {
        UserResponse userResponse = userService.register(userRegisterBody);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginBody userLoginBody) {
        authenticate(userLoginBody.getEmail(), userLoginBody.getPassword());
        User user = userService.login(userLoginBody);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        HttpHeaders httpHeaders = getHeaders(user, userPrincipal);
        UserResponse userResponse = new UserToUserResponse().convert(user);
        return new ResponseEntity<>(userResponse, httpHeaders, HttpStatus.OK);
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

    @PutMapping("/resetpass")
    public ResponseEntity<?> resetPasswordSavePass(@RequestBody UserResetPasswordBody userResetPasswordBody) {
        userService.resetPasswordSave(userResetPasswordBody);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/newpass")
    public ResponseEntity<UserResponse> setNewPassword(@RequestParam("pass") String newPassword,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                       @RequestHeader(SUM_CONTROL_ID) String checkSumId,
                                                       @RequestHeader(SUM_CONTROL_PROPERTIES) String checkSumProperties) {


        User user = authenticate();
        validToken(user, token, checkSumId, checkSumProperties);
        userService.setNewPassword(user, newPassword);
        UserPrincipal userPrincipal = new UserPrincipal(user);
        HttpHeaders httpHeaders = getHeaders(user, userPrincipal);
        UserResponse userResponse = new UserToUserResponse().convert(user);

        return new ResponseEntity<>(userResponse, httpHeaders, HttpStatus.OK);
    }


//    //only for tests - remove or change this method
//    @GetMapping()
//    public ResponseEntity<List<UserResponse>> findAll(@RequestHeader HttpHeaders headers) {
//        List<String> strings = headers.get(headers.AUTHORIZATION);
//        String token = strings.stream().findFirst().get();
//
//        String username = null;
//        if (token != null && token.startsWith(TOKEN_PREFIX)) {
//            token = token.substring(TOKEN_PREFIX.length());
//            username = jwtTokenProvider.getSubject(token);
//        }
//
//        log.info(username);
//        List<UserResponse> users = userService.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

    private HttpHeaders getHeaders(User user, UserPrincipal userPrincipal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String jwtToken = jwtTokenProvider.generateJwtToken(userPrincipal);
        httpHeaders.add(JWT_TOKEN_HEADER, jwtToken);
        TokenCheckSumResponse tokenCheckSum = userService.generateCheckSum(user, jwtToken);
        httpHeaders.add(SUM_CONTROL_ID, String.valueOf(tokenCheckSum.getId()));
        httpHeaders.add(SUM_CONTROL_PROPERTIES, tokenCheckSum.getSum());
        return httpHeaders;
    }

    private void authenticate(String email, String password) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            Object principal = authenticate.getPrincipal();
            if (principal instanceof UserPrincipal userPrincipal) {
                userService.checkBans(userPrincipal.getUser());
            }
        } catch (LockedException exception) {
            userService.accountBlocked(email);
            throw new LockedException(exception.getMessage());
        }
    }

    private User authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(authentication.getName());
        return optionalUser.orElse(null);
    }

    private void validToken(User user, String token, String checkSumId, String checkSumProperties) {
        boolean isValidToken = userService.isValidToken(user, token, checkSumId, checkSumProperties);
        if (!isValidToken) {
            throw new BadCredentialsException("Token is not valid");
        }
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
