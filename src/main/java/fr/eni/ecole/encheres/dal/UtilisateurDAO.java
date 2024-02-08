
package fr.eni.ecole.encheres.dal;

import java.util.List;
import java.util.Optional;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

public interface UtilisateurDAO {

	Optional<Utilisateur> findByPseudo(String pseudo);

	Optional<Utilisateur> findByEmail(String email);

	void modifierUtilisateur(Utilisateur utilisateur);

	void supprimerUtilisateur(Utilisateur utilisateur);
	
	List<Utilisateur> findAll();

}
