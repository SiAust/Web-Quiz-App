package io.github.siaust.web_quiz_app.Config;

import io.github.siaust.web_quiz_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    /* This sets up the security on specified paths according to role of client */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/**").authenticated()
                .antMatchers("/api/quizzes/**").authenticated()
                .antMatchers(  "/", "/api/register").permitAll()
//                .antMatchers("/**").permitAll()
                .and()
                    .httpBasic()
                .and()
                    .csrf().disable()
                .headers()
                    .frameOptions().disable()
                .and() // todo: fix logout
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

    }

    /* This sets up the user roles, so they can access the endpoints configured above */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.jdbcAuthentication()
//                .dataSource(dataSource);


        auth.userDetailsService(userDetailsService);
//                .passwordEncoder(new BCryptPasswordEncoder());


//        auth.inMemoryAuthentication()
//                .passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
//                .withUser("user1")
//                .password("password")
//                .roles("USER");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
