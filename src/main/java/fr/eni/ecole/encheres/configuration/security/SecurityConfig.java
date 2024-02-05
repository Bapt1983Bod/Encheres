package fr.eni.ecole.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
	

	
	// Authentification des users depuis la bdd
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager
				.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 from UTILISATEURS where pseudo = ?");
		jdbcUserDetailsManager
				.setAuthoritiesByUsernameQuery("SELECT pseudo, administrateur as ROLE from UTILISATEURS where pseudo = ?");
		return jdbcUserDetailsManager;
	}

	// Gestion des accès au page
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> 
			auth.requestMatchers(HttpMethod.GET, "/", "/accueil").permitAll()
				.requestMatchers(HttpMethod.GET, "/test").authenticated()
				.anyRequest().denyAll());

		// Paramétrage de la page de login
		http.formLogin(form -> {
			form.loginPage("/login").permitAll();
			form.defaultSuccessUrl("/accueil");
		});

		// Paramétrage page de logout
		http.logout(logout -> logout.invalidateHttpSession(true) // invalidation de la session http
				.deleteCookies("JSESSIONID") // supression cookie
				.clearAuthentication(true) // vide champ de saisie
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //
				.logoutSuccessUrl("/") // ce qui est fait après la déconnection (avec paramètre précisant qu'il d'agit
										// d'une deconnection
				.permitAll() // détermine qui a accès à la deconnection
		);

		return http.build();
	}
}
