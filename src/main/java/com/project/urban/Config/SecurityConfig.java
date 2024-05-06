package com.project.urban.Config;

import com.project.urban.Service.Impl.UserServiceImpl;
import com.project.urban.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/login", "/resetpassword", "/test", "/**", "/api/users/current-user",
                                "/api/users/current-user/")
                        .permitAll()
                        .requestMatchers("/api/admin/**").hasAuthority("Role_Admin")
                        .requestMatchers("/api/users/**").hasAnyAuthority("Role_User", "Role_Admin")
                        .anyRequest().authenticated()

                ).formLogin(formLogin -> formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .successHandler((req, res, auth) -> {
                            for (GrantedAuthority authority : auth.getAuthorities()) {
                                if (authority.getAuthority().equals("Role_User")) {
                                    res.sendRedirect("/homeuser");
                                } else if (authority.getAuthority().equals("Role_Admin")) {
                                    res.sendRedirect("/homeadmin");
                                }
                            }
                        })
                        .permitAll());
        http
                .csrf((csrf) -> csrf.disable());
        return http.build();
    }
}
