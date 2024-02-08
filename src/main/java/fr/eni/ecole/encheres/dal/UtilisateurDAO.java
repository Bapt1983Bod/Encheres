
package fr.eni.ecole.encheres.dal;

import java.util.Optional;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

public interface UtilisateurDAO {

	Optional<Utilisateur> findByPseudo(String pseudo);

	Optional<Utilisateur> findByEmail(String email);

	void modifierUtilisateur(Utilisateur utilisateur) throws BusinessException;

	void supprimerUtilisateur(Utilisateur utilisateur);

}
