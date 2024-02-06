package fr.eni.ecole.encheres.dal;

import java.util.Optional;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	Optional<Utilisateur> findByPseudo(String pseudo);

	Optional<Utilisateur> findByEmail(String email);

	Utilisateur save(Utilisateur utilisateur);

}
