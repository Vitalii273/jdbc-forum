package com.telran.jdbcforum.config;


import com.telran.jdbcforum.business.CustomDetailsService;
import com.telran.jdbcforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity()
public class SecurityConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Configuration
    static class AppSecurityConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/account/all").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/account/{userName}").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/comments/all").permitAll()
                    .antMatchers(HttpMethod.POST,"/account").permitAll()
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic();
//                    .authorizeRequests()
//                    .antMatchers(HttpMethod.GET,"/account/all").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.DELETE,"/account/{userName}").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.DELETE,"/comments/all").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST,"/account").permitAll()
//                    .anyRequest().permitAll()
//                    .and()
//                    .httpBasic();
        }
    }
}
