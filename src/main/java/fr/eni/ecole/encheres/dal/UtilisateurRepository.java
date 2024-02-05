package fr.eni.ecole.encheres.dal;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public interface UtilisateurRepository {

	Optional<Utilisateur> findByPseudo(String pseudo);

	Optional<Utilisateur> findByEmail(String email);

	Utilisateur save(Utilisateur utilisateur);

}
