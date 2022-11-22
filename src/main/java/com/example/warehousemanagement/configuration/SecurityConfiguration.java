package com.example.warehousemanagement.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/","/home").permitAll()
                .antMatchers("/admin",
                        "/admin/users",
                        "/admin/users/add",
                        "/admin/users/delete/{id}",
                        "/admin/users/update/{id}").hasAnyAuthority("SYSTEM_ADMIN")
                .antMatchers("/client",
                        "/client/orders",
                        "/client/orders/add",
                        "/client/order/{status}",
                        "/client/vieworder/{orderNumber}").hasAnyAuthority("CLIENT")
                .antMatchers("/manager",
                        "/manager/items",
                        "/manager/items/add",
                        "/manager/items/delete/{id}",
                        "/manager/items/update/{id}",
                        "/manager/trucks",
                        "/manager/trucks/add",
                        "/manager/trucks/delete/{id}",
                        "/manager/trucks/update/{id}").hasAnyAuthority("WAREHOUSE_MANAGER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/",true)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){return NoOpPasswordEncoder.getInstance();
    }
}
