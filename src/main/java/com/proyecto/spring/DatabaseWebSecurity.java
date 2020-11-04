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
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration(proxyBeanMethods = true)
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
                .authoritiesByUsernameQuery("select username , nom_perfil "
                        + " from usuario u inner join perfil p on p.id_perfil = u.id_perfil where username =?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests().antMatchers("/", "/staff", "/servicios", "/recuperar",
                "/paciente/**", "/nutricionista/verImagen/**", "/enviarToken" ,"/login/newPassword/**",
                "/reestablecer", "/reestablecer-clave","/validarToken",
                "/css/**", "/js/**", "/img/**", "/bootbox/**" ,"/webfonts/**").permitAll()
                // Asignar permisos 
                .antMatchers("/admin/**").hasAnyAuthority("Administrador")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/acceso").permitAll().
                failureUrl("/login?error=true").and().
                logout().permitAll().logoutSuccessUrl("/login?logout");
        
    }
}
