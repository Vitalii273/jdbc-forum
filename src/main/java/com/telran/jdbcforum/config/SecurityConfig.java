package com.telran.jdbcforum.config;


import com.telran.jdbcforum.business.CustomDetailsService;
import com.telran.jdbcforum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder encoder(){
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService()).passwordEncoder(encoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/account/all").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/account/{userName}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/comments/all").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/account").permitAll()
                    .anyRequest().permitAll()
                    .and()
                    .httpBasic();
        }
}
