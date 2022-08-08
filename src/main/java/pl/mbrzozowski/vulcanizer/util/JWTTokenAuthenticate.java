package pl.mbrzozowski.vulcanizer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.mbrzozowski.vulcanizer.domain.UserPrincipal;
import pl.mbrzozowski.vulcanizer.entity.User;
import pl.mbrzozowski.vulcanizer.service.UserServiceImpl;

import java.util.Optional;

@Component
public class JWTTokenAuthenticate {
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;

    @Lazy
    @Autowired
    public JWTTokenAuthenticate(AuthenticationManager authenticationManager, UserServiceImpl userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    public void authenticate(String email, String password) {
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

    public User authenticate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userService.findByEmail(authentication.getName());
        User user = optionalUser.orElse(null);
        if (user != null) {
            userService.checkBans(user);
        }
        return user;
    }

    public void validToken(User user, String token, String checkSumId, String checkSumProperties) {
        boolean isValidToken = userService.isValidToken(user, token, checkSumId, checkSumProperties);
        if (!isValidToken) {
            throw new BadCredentialsException("Token is not valid");
        }
    }
}
