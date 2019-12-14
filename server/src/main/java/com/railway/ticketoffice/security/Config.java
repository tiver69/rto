package com.railway.ticketoffice.security;

import com.railway.ticketoffice.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.railway.ticketoffice.security.Constants.SING_UP_URLS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class Config extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private PassengerService passengerService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(passengerService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()// working with jwt
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint) //set error component
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // working with jwt - do not need session
                .and()
                .headers().frameOptions().sameOrigin()//to enable H2 database
                .and()
                .authorizeRequests().antMatchers(
                "/",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js").permitAll() //do not need authentication for such items
                .antMatchers(SING_UP_URLS.get(0),SING_UP_URLS.get(1)).permitAll() //TO_DO: refactor for proper param passing
                .anyRequest().authenticated(); //all other requests require authentication
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
