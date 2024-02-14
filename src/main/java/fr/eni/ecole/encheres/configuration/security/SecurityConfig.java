
package fr.eni.ecole.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

	// Encodage mdp avec Bcrypt
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Authentification des users depuis la bdd
	@Bean
	UserDetailsManager userDetailsManager(DataSource dataSource) {

		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager
				.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 from UTILISATEURS where pseudo = ?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT UTILISATEURS.pseudo, ROLES.ROLE FROM UTILISATEURS INNER JOIN ROLES ON UTILISATEURS.administrateur = ROLES.IS_ADMIN where pseudo= ?");
		return jdbcUserDetailsManager;
	}

	// Gestion des accès aux pages
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/", "/accueil").permitAll()
				.requestMatchers(HttpMethod.GET, "/profil").hasAnyRole("ADMIN", "UTILISATEUR")
				.requestMatchers(HttpMethod.GET, "/modification-profil").hasAnyRole("ADMIN", "UTILISATEUR")
				.requestMatchers(HttpMethod.POST, "/modification-profil").hasAnyRole("ADMIN", "UTILISATEUR")
				.requestMatchers(HttpMethod.POST, "/supprimer-compte").authenticated()
				.requestMatchers(HttpMethod.GET, "/inscription").permitAll()
				.requestMatchers(HttpMethod.POST, "/inscription").permitAll()
				.requestMatchers(HttpMethod.GET, "/acheter").hasAnyRole("ADMIN", "UTILISATEUR")
				.requestMatchers(HttpMethod.POST, "/encherir").hasAnyRole("ADMIN", "UTILISATEUR")
				.requestMatchers(HttpMethod.POST, "/surenchere").hasAnyRole("ADMIN", "UTILISATEUR")
				.requestMatchers(HttpMethod.POST, "/filtres").permitAll()
				.requestMatchers(HttpMethod.GET, "/vendre").authenticated()
				.requestMatchers(HttpMethod.POST, "/vendre").authenticated()
				.requestMatchers(HttpMethod.GET, "/modificationArticle").authenticated()
				.requestMatchers(HttpMethod.POST, "/modificationArticle").authenticated()
				.requestMatchers(HttpMethod.GET, "/administration").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/suppressionAdmin").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/desactivation").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/activation").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/admin").hasRole("ADMIN")
				.requestMatchers("/css/*").permitAll()
				.requestMatchers("/img/*").permitAll()
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
