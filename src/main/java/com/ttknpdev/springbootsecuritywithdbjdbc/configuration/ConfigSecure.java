package com.ttknpdev.springbootsecuritywithdbjdbc.configuration;

import com.ttknpdev.springbootsecuritywithdbjdbc.log.Mylog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfigSecure {
    /* An instance of DataSource object will be created and injected by Spring framework */
    private final DataSource dataSource;
    @Autowired
    public ConfigSecure(DataSource dataSource) {
        /*
           When CDI is working
           It will read database connection information from the application.properties file.
           Mylog.configSecure.info("ConfigSecure() is working");
        */
        this.dataSource = dataSource;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        /*
            As you can see, we need to specify a password encoder (BCrypt is recommended),
            data source and two SQL statements: the first one selects a user based on username, and the second one selects role of the user.
            Note that Spring security requires the column names must be username, password, enabled and role.
        */
        // Mylog.configSecure.info("configAuthentication(AuthenticationManagerBuilder) is working");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        auth.jdbcAuthentication()
                .passwordEncoder(bCryptPasswordEncoder)
                .dataSource(dataSource)
                /* two SQL statements: the first one selects a user based on username, and the second one selects role of the user.  */
                // Note that Spring security requires the column names must be username, password, enabled and role.
                .usersByUsernameQuery("select username , password , enabled from users where username = ?") // ? = peter
                .authoritiesByUsernameQuery("select username , role from users where username = ?"); // ? = peter
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(HttpMethod.GET,"/three").hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.GET,"/three/**").hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.GET,"/one/**").hasRole("ADMIN")
                                 .requestMatchers(HttpMethod.GET,"/two").hasRole("ADMIN")
                                /*
                                   That is Worked !!!!  It will check role in database of username ?
                                   And it cuts ROLE_ ?
                                */
                        .anyRequest().authenticated()
                ).formLogin()
                .permitAll();
        return http.build();
    }
}
