package com.proyecto.spring;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource data;

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates");
        return bean;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(data)
                .usersByUsernameQuery("select username , pass , estado from usuario where username = ?")
                .authoritiesByUsernameQuery("select u.username , p.nom_perfil "
                        + " from usuario u inner join perfil p on p.id_perfil = u.id_perfil where u.username =?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().antMatchers("/", "/staff", "/servicios", "/recuperar",
                "/guardar", "/registrar", "/nutricionista/verImagen/**", "/enviarToken", "/login/newPassword/**",
                "/reestablecer", "/reestablecer-clave", "/validarToken", "/acceso", "/activarCuenta", "/enviarTokenPac",
                "/verImagenLogeado", "/datosUsuario", "/VerImagenAjaxLogeado", "/confirmarActivarCuenta",
                "/css/**", "/js/**", "/img/**", "/bootbox/**", "/webfonts/**", "paypal/**").permitAll()
                // Asignar permisos 
                .antMatchers("/historia/**").hasAnyAuthority("Paciente", "Administrador", "Nutricionista")
                .antMatchers("/admin/**").hasAnyAuthority("Administrador")
                .antMatchers("/contextura/**").hasAnyAuthority("Administrador", "Nutricionista")
                .antMatchers("/citas/**").hasAnyAuthority("Paciente", "Administrador", "Nutricionista")
                .antMatchers("/alimentos/**").hasAnyAuthority("Administrador", "Nutricionista")
                .antMatchers("/antropometrico/**").hasAnyAuthority("Administrador", "Nutricionista", "Paciente")
                .antMatchers("/paciente/**").hasAnyAuthority("Administrador", "Paciente", "Nutricionista")
                .antMatchers("/nutricionista/**").hasAnyAuthority("Administrador", "Paciente", "Nutricionista")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/acceso").permitAll().
                failureUrl("/login?error=true").and().
                logout().permitAll().logoutSuccessUrl("/login?logout");

        /*
                  .permitAll().clearAuthentication(true)
                .invalidateHttpSession(true).logoutSuccessUrl("/login?logout");
         */
    }
}
