package de.neuefische.backend.security;

import de.neuefische.backend.service.AppUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class     SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AppUserDetailService appUserDetailService;

    public SecurityConfig(AppUserDetailService appUserDetailService) {
        this.appUserDetailService = appUserDetailService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/route/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/found-routes/**").permitAll()
                .antMatchers("/api/user/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/photo/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/route").authenticated()
                .antMatchers(HttpMethod.POST, "/api/route/photos/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/route/photos/*").authenticated()
                .antMatchers(HttpMethod.POST, "/api/route/comments/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/api/route/comments/*").authenticated()
//                .antMatchers("/api/**").hasRole("ADMIN")
                .and().httpBasic().and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailService);
    }
}
