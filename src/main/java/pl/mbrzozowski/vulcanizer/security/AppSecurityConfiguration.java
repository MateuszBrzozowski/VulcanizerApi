package pl.mbrzozowski.vulcanizer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration implements WebMvcConfigurer {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppSecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/v1/business/recommend").permitAll()
                .antMatchers("/api/v1/users/login").permitAll()
//                .antMatchers("/api/v1/users").hasRole(AppUserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
        return http.build();
    }


//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> {
//            web.ignoring().antMatchers("/", "index", "/css/*", "/js/*");
//            web.ignoring().antMatchers("/api/v1/business/recommend");
//        };
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("pass"))
                .roles(AppUserRole.USER.name())
                .authorities(AppUserRole.USER.getGrantedAuthorityList())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("pass"))
//                .roles(AppUserRole.ADMIN.name())
                .authorities(AppUserRole.ADMIN.getGrantedAuthorityList())
                .build();

        UserDetails businessOwner = User.builder()
                .username("bo")
                .password(passwordEncoder.encode("pass"))
                .roles(AppUserRole.BUSINESS_OWNER.name())
                .build();

        UserDetails businessEmployee = User.builder()
                .username("be")
                .password(passwordEncoder.encode("pass"))
                .roles(AppUserRole.BUSINESS_EMPLOYEE.name())
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
