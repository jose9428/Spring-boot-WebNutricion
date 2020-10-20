package com.proyecto.spring;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration(proxyBeanMethods = true)
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource data;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(data)
                .usersByUsernameQuery("select username , pass , estado from usuario where username = ?")
                .authoritiesByUsernameQuery("select username , nom_perfil "
                        + " from usuario u inner join perfil p on p.id_perfil = u.id_perfil where username =?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().antMatchers("/", "/staff", "/servicios","/paciente/**", "/nutricionista/verImagen/**",
                "/css/**", "/js/**", "/img/**", "/bootbox/**").permitAll()
                // Asignar permisos 
                .antMatchers("/admin/**").hasAnyAuthority("Administrador")
                // Todas las demas url deben autenticarse
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/staff").permitAll().
                failureUrl("/login?error=true").and().
                logout().permitAll().logoutSuccessUrl("/login?logout");
        // .and().formLogin().permitAll() ; // Simple
    }
}
