
package fr.eni.ecole.encheres.bll;

import java.util.Optional;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

public interface UtilisateurService {

	Utilisateur creerUtilisateur(Utilisateur utilisateur) throws BusinessException;

	Optional<Utilisateur> findByPseudo(String pseudo);

//	Utilisateur getUtilisateurConnecte();

}
