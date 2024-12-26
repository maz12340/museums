package org.example.museum.config;

import org.example.museum.model.User;
import org.example.museum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Конфигурация безопасности приложения.
 * <p>
 * Этот класс настраивает безопасность приложения, включая аутентификацию пользователей,
 * авторизацию, настройку страницы входа и выхода, а также управление доступом на основе ролей.
 * Включает использование кастомного сервиса для работы с пользователями и ролями.
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserService userService;

    /**
     * Конструктор, инициализирующий сервис пользователей.
     *
     * @param userService сервис для работы с пользователями
     */
    @Autowired
    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    /**
     * Бин для кодирования паролей с использованием BCrypt.
     *
     * @return PasswordEncoder, использующий BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Бин для загрузки данных пользователя по имени пользователя.
     * Используется для аутентификации пользователей.
     *
     * @return UserDetailsService, который загружает данные пользователя по имени
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);
            if (user != null) {
                return new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
                                .toList());
            }
            throw new RuntimeException("User not found");
        };
    }
}
